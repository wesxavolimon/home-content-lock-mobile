package com.homecontentlock.data.repository

import com.homecontentlock.data.local.db.BlockerDatabase
import com.homecontentlock.data.local.db.ConfigDao
import com.homecontentlock.data.local.db.ConfigEntity
import com.homecontentlock.data.local.db.LogDao
import com.homecontentlock.data.local.db.LogEntity
import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.model.LogEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.Instant

class BlockerRepositoryImplTest {

    private lateinit var database: BlockerDatabase
    private lateinit var configDao: ConfigDao
    private lateinit var logDao: LogDao
    private lateinit var repository: BlockerRepositoryImpl

    @Before
    fun setup() {
        database = mock(BlockerDatabase::class.java)
        configDao = mock(ConfigDao::class.java)
        logDao = mock(LogDao::class.java)

        `when`(database.configDao()).thenReturn(configDao)
        `when`(database.logDao()).thenReturn(logDao)

        repository = BlockerRepositoryImpl(database)
    }

    @Test
    fun getBlockerStatus_returnsEnabled_whenConfigExists() = runTest {
        `when`(configDao.getValue("blocker_status")).thenReturn(BlockerStatus.Enabled.name)

        val status = repository.getBlockerStatus()

        assertEquals(BlockerStatus.Enabled, status)
    }

    @Test
    fun getBlockerStatus_returnsDisabled_whenConfigDoesNotExist() = runTest {
        `when`(configDao.getValue("blocker_status")).thenReturn(null)

        val status = repository.getBlockerStatus()

        assertEquals(BlockerStatus.Disabled, status)
    }

    @Test
    fun setBlockerStatus_savesToDatabase() = runTest {
        repository.setBlockerStatus(BlockerStatus.Enabled)

        Mockito.verify(configDao).upsert(ConfigEntity("blocker_status", BlockerStatus.Enabled.name))
    }

    @Test
    fun addLogEntry_savesToDatabase() = runTest {
        val logEntry = LogEntry(
            timestamp = Instant.now(),
            action = "TEST",
            status = BlockerStatus.Enabled
        )
        val logEntity = LogEntity.fromDomainModel(logEntry)

        repository.addLogEntry(logEntry)

        Mockito.verify(logDao).insert(logEntity)
    }

    @Test
    fun getAllLogEntries_returnsFlowOfLogs() = runTest {
        val logEntity = LogEntity(
            timestamp = Instant.now().toEpochMilli(),
            action = "TEST",
            status = BlockerStatus.Enabled.name
        )
        `when`(logDao.getAll()).thenReturn(flowOf(listOf(logEntity)))

        val logs = repository.getAllLogEntries().first()

        assertEquals(1, logs.size)
        assertEquals("TEST", logs[0].action)
    }
}