package com.homecontentlock.data.repository

import com.homecontentlock.data.local.db.BlockerDatabase
import com.homecontentlock.data.local.db.ConfigEntity
import com.homecontentlock.data.local.db.LogEntity
import com.homecontentlock.data.local.db.CustomSiteEntity
import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.model.CustomBlockedSite
import com.homecontentlock.domain.model.LogEntry
import com.homecontentlock.domain.repository.IBlockerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockerRepositoryImpl @Inject constructor(
    private val database: BlockerDatabase
) : IBlockerRepository {

    override suspend fun getBlockerStatus(): BlockerStatus {
        val statusStr = database.configDao().getValue("blocker_status")
        return if (statusStr != null) {
            BlockerStatus.valueOf(statusStr)
        } else {
            BlockerStatus.Disabled
        }
    }

    override suspend fun setBlockerStatus(status: BlockerStatus) {
        database.configDao().upsert(ConfigEntity("blocker_status", status.name))
    }

    override suspend fun addLogEntry(logEntry: LogEntry) {
        database.logDao().insert(LogEntity.fromDomainModel(logEntry))
    }

    override fun getAllLogEntries(): Flow<List<LogEntry>> {
        return database.logDao().getAll().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun deleteOldLogs() {
        val threshold = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000) // 30 days ago
        database.logDao().deleteOldLogs(threshold)
    }

    override suspend fun addCustomSite(site: CustomBlockedSite) {
        database.customSiteDao().insert(CustomSiteEntity.fromDomainModel(site))
    }

    override suspend fun removeCustomSite(domain: String) {
        database.customSiteDao().delete(domain)
    }

    override fun getCustomSites(): Flow<List<CustomBlockedSite>> {
        return database.customSiteDao().getActive().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}