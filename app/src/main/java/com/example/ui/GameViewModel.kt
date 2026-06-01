package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GameRepository
import com.example.data.UserProgress
import com.example.model.Riddle
import com.example.model.easyRiddles
import com.example.model.hardRiddles
import com.example.model.impossibleRiddle
import com.example.data.GeminiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class GameState(
    val progress: UserProgress = UserProgress(),
    val currentRiddle: Riddle? = null,
    val showHint: Boolean = false,
    val isAlmostThere: Boolean = false,
    val isCorrect: Boolean = false,
    val aiHint: String = "",
    val isLoadingAi: Boolean = false
)

class GameViewModel(
    private val repository: GameRepository,
    private val geminiService: GeminiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.progress.collect { progress ->
                if (progress == null) {
                    repository.saveProgress(UserProgress())
                } else {
                    _uiState.value = _uiState.value.copy(
                        progress = progress,
                        currentRiddle = getRiddleForProgress(progress),
                        showHint = false,
                        isAlmostThere = false,
                        isCorrect = false
                    )
                }
            }
        }
    }

    private fun isAnswerCorrect(userAnswer: String, correctAnswer: String): Boolean {
        fun normalize(text: String): String {
            return text.lowercase()
                .replace(Regex("[áàâãä]"), "a")
                .replace(Regex("[éèêë]"), "e")
                .replace(Regex("[íìîï]"), "i")
                .replace(Regex("[óòôõö]"), "o")
                .replace(Regex("[úùûü]"), "u")
                .replace(Regex("[ç]"), "c")
                .replace(Regex("[^a-z0-9 ]"), "") // remove punctuation
                .replace(Regex("\\s+"), " ")      // normalize spaces
                .trim()
        }

        val normUser = normalize(userAnswer)
        val normCorrect = normalize(correctAnswer)

        if (normUser == normCorrect) return true

        // Remove common Portuguese articles at the start: "o ", "a ", "os ", "as ", "um ", "uma ", "uns ", "umas "
        fun removeArticles(text: String): String {
            return text.replace(Regex("^(o|a|os|as|um|uma|uns|umas)\\s+"), "")
        }

        val userNoArticles = removeArticles(normUser)
        val correctNoArticles = removeArticles(normCorrect)

        if (userNoArticles == correctNoArticles) return true

        // Also check if any word matches exactly
        if (userNoArticles.contains(correctNoArticles) || correctNoArticles.contains(userNoArticles)) {
            // But only if it's not too short to avoid false positives
            if (correctNoArticles.length >= 3 && userNoArticles.length >= 3) {
                return true
            }
        }

        return false
    }

    private fun getRiddleForProgress(progress: UserProgress): Riddle? {
        if (progress.isFinished) return null
        return when (progress.currentLevel) {
            "EASY" -> easyRiddles.getOrNull(progress.currentQuestionIndex) ?: checkTransition(progress)
            "HARD" -> hardRiddles.getOrNull(progress.currentQuestionIndex) ?: checkTransition(progress)
            "IMPOSSIBLE" -> impossibleRiddle
            else -> null
        }
    }

    private fun checkTransition(progress: UserProgress): Riddle? {
        // This is a fallback if index goes out of bounds before saving new level
        return null 
    }

    fun submitAnswer(answer: String) {
        val state = _uiState.value
        val riddle = state.currentRiddle ?: return
        val currentLevel = state.progress.currentLevel

        if (currentLevel == "IMPOSSIBLE") {
            if (answer.length > 5) { // Needs some substance
                _uiState.value = state.copy(isCorrect = true)
                viewModelScope.launch {
                    delay(1500)
                    val earnedPoints = if (state.showHint) 25 else 100
                    val p = state.progress.copy(isFinished = true, score = state.progress.score + earnedPoints)
                    repository.saveProgress(p)
                }
            } else {
                _uiState.value = state.copy(isAlmostThere = true)
            }
            return
        }

        val correctAnswer = riddle.answer
        val userAnswer = answer

        if (isAnswerCorrect(userAnswer, correctAnswer)) {
            // Correct
            val (nextLevel, nextIndex, newScore) = calculateNextProgress(state.progress, state.showHint)
            val isFinished = nextLevel == "DONE"
            
            _uiState.value = state.copy(isCorrect = true)

            viewModelScope.launch {
                delay(1500)
                repository.saveProgress(
                    state.progress.copy(
                        currentLevel = if (isFinished) state.progress.currentLevel else nextLevel,
                        currentQuestionIndex = nextIndex,
                        score = newScore,
                        isFinished = isFinished
                    )
                )
            }
        } else {
            // Wrong
            _uiState.value = state.copy(isAlmostThere = false)
        }
    }

    private fun calculateNextProgress(progress: UserProgress, usedHint: Boolean): Triple<String, Int, Int> {
        var level = progress.currentLevel
        var index = progress.currentQuestionIndex + 1
        
        val basePoints = if (level == "EASY") 10 else 20
        val earnedPoints = if (usedHint) basePoints / 2 else basePoints
        var score = progress.score + earnedPoints

        if (level == "EASY" && index >= easyRiddles.size) {
            level = "HARD"
            index = 0
            score += 50
        } else if (level == "HARD" && index >= hardRiddles.size) {
            level = "IMPOSSIBLE"
            index = 0
            score += 100
        } else if (level == "IMPOSSIBLE") {
            level = "DONE"
        }

        return Triple(level, index, score)
    }

    fun revealHint() {
        val state = _uiState.value
        if (!state.showHint) {
            _uiState.value = state.copy(showHint = true)
            // Penalty for hint is applied when calculating points upon completion
        }
    }

    fun askOracle() {
        val state = _uiState.value
        val riddle = state.currentRiddle ?: return
        
        if (state.isLoadingAi) return

        _uiState.value = state.copy(isLoadingAi = true)
        
        viewModelScope.launch {
            val response = geminiService.generateHint(riddle.question, riddle.hint)
            _uiState.value = _uiState.value.copy(
                aiHint = response,
                isLoadingAi = false
            )
        }
    }

    fun resetGame() {
        viewModelScope.launch {
            repository.resetGame()
        }
    }

    fun selectAvatar(avatarName: String) {
        val progress = _uiState.value.progress
        viewModelScope.launch {
            repository.saveProgress(progress.copy(selectedAvatar = avatarName))
        }
    }
}
