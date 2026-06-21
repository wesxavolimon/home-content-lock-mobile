package com.homecontentlock.domain.repository

import com.homecontentlock.domain.model.ActivationSecret

interface IPasswordValidator {
    suspend fun validate(password: String): Boolean
    suspend fun save(password: String)
    suspend fun getActivationSecret(): ActivationSecret?
}