package com.homecontentlock.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomSiteDao {
    @Insert
    suspend fun insert(customSiteEntity: CustomSiteEntity)

    @Query("SELECT * FROM custom_sites WHERE isActive = 1")
    fun getActive(): Flow<List<CustomSiteEntity>>

    @Query("DELETE FROM custom_sites WHERE domain = :domain")
    suspend fun delete(domain: String)
}