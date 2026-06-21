package com.homecontentlock.domain.model

import java.time.Instant

data class CustomBlockedSite(
    val id: Long = 0,
    val domain: String,
    val addedAt: Instant,
    val isActive: Boolean = true
)