package com.homecontentlock.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(configEntity: ConfigEntity)

    @Query("SELECT value FROM config WHERE key = :key")
    suspend fun getValue(key: String): String?

    @Query("SELECT * FROM config")
    fun getConfigs(): Flow<List<ConfigEntity>>
}