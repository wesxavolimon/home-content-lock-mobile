package com.homecontentlock.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.homecontentlock.domain.model.BlockerStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.time.Instant

class BlockerDatabaseTest {
    private lateinit var database: BlockerDatabase
    private lateinit var logDao: LogDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, BlockerDatabase::class.java
        ).build()
        logDao = database.logDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun writeLogAndReadInList() = runTest {
        val logEntry = LogEntity(
            timestamp = Instant.now().toEpochMilli(),
            action = "ENABLE",
            status = BlockerStatus.Enabled.name,
            details = "Test log"
        )
        logDao.insert(logEntry)

        val logs = logDao.getAll().first()
        assertEquals(1, logs.size)
        assertEquals("ENABLE", logs[0].action)
        assertEquals(BlockerStatus.Enabled.name, logs[0].status)
    }

    @Test
    fun deleteOldLogs() = runTest {
        val oldLog = LogEntity(
            timestamp = Instant.now().minusSeconds(30L * 24 * 60 * 60).toEpochMilli(), // 30 days ago
            action = "OLD",
            status = BlockerStatus.Disabled.name
        )
        val newLog = LogEntity(
            timestamp = Instant.now().toEpochMilli(),
            action = "NEW",
            status = BlockerStatus.Enabled.name
        )
        
        logDao.insert(oldLog)
        logDao.insert(newLog)

        val threshold = Instant.now().minusSeconds(15L * 24 * 60 * 60).toEpochMilli() // 15 days ago
        logDao.deleteOldLogs(threshold)

        val logs = logDao.getAll().first()
        assertEquals(1, logs.size)
        assertEquals("NEW", logs[0].action)
    }
}