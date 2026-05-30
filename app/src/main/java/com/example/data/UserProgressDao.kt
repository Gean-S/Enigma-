package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun getUserProgress(): Flow<UserProgress?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: UserProgress)

    @Query("UPDATE user_progress SET score = score + :points WHERE id = 1")
    suspend fun addScore(points: Int)

    @Query("UPDATE user_progress SET name = :newName WHERE id = 1")
    suspend fun updateName(newName: String)
    
    @Query("DELETE FROM user_progress")
    suspend fun clearProgress()
}
