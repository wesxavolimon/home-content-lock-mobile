package com.homecontentlock.domain.model

import java.time.Instant

data class LogEntry(
    val id: Long = 0,
    val timestamp: Instant,
    val action: String,
    val status: BlockerStatus,
    val details: String? = null
)