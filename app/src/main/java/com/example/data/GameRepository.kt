package com.example.data

import kotlinx.coroutines.flow.Flow

class GameRepository(private val userProgressDao: UserProgressDao) {
    val progress: Flow<UserProgress?> = userProgressDao.getUserProgress()

    suspend fun saveProgress(progress: UserProgress) {
        userProgressDao.insertOrUpdateProgress(progress)
    }

    suspend fun updateScore(points: Int) {
        userProgressDao.addScore(points)
    }

    suspend fun resetGame() {
        userProgressDao.clearProgress()
        userProgressDao.insertOrUpdateProgress(UserProgress())
    }
}
