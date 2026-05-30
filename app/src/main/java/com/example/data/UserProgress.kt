package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val id: Int = 1,
    val score: Int = 0,
    val currentLevel: String = "EASY",
    val currentQuestionIndex: Int = 0,
    val name: String = "Jogador",
    val isFinished: Boolean = false,
    val selectedAvatar: String = "novice"
)
