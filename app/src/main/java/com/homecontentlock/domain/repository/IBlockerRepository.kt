package com.homecontentlock.domain.repository

import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.model.CustomBlockedSite
import com.homecontentlock.domain.model.LogEntry
import kotlinx.coroutines.flow.Flow

interface IBlockerRepository {
    suspend fun getBlockerStatus(): BlockerStatus
    suspend fun setBlockerStatus(status: BlockerStatus)

    suspend fun addLogEntry(logEntry: LogEntry)
    fun getAllLogEntries(): Flow<List<LogEntry>>
    suspend fun deleteOldLogs()

    suspend fun addCustomSite(site: CustomBlockedSite)
    suspend fun removeCustomSite(domain: String)
    fun getCustomSites(): Flow<List<CustomBlockedSite>>
}