package com.homecontentlock.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.homecontentlock.domain.model.BlockerStatus
import java.time.Instant

@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long, // Store as Long for Room compatibility
    val action: String,
    val status: String, // Store enum as String
    val details: String? = null
) {
    fun toDomainModel(): com.homecontentlock.domain.model.LogEntry {
        return com.homecontentlock.domain.model.LogEntry(
            id = id,
            timestamp = Instant.ofEpochMilli(timestamp),
            action = action,
            status = BlockerStatus.valueOf(status),
            details = details
        )
    }

    companion object {
        fun fromDomainModel(model: com.homecontentlock.domain.model.LogEntry): LogEntity {
            return LogEntity(
                id = model.id,
                timestamp = model.timestamp.toEpochMilli(),
                action = model.action,
                status = model.status.name,
                details = model.details
            )
        }
    }
}