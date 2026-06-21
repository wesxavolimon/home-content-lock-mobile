package com.homecontentlock.domain.model

import java.time.Instant

data class ActivationSecret(
    val passwordHash: String,
    val createdAt: Instant
)