package com.homecontentlock.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.homecontentlock.domain.model.LogEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    @Insert
    suspend fun insert(logEntity: LogEntity)

    @Query("SELECT * FROM logs ORDER BY timestamp DESC")
    fun getAll(): Flow<List<LogEntity>>

    @Query("SELECT * FROM logs ORDER BY timestamp DESC LIMIT :limit")
    fun getWithLimit(limit: Int): Flow<List<LogEntity>>

    @Query("DELETE FROM logs WHERE timestamp < :threshold")
    suspend fun deleteOldLogs(threshold: Long)
}