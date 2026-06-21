package com.homecontentlock.usecase

import com.homecontentlock.domain.repository.IPasswordValidator
import javax.inject.Inject

class SetPasswordUseCase @Inject constructor(
    private val passwordValidator: IPasswordValidator
) {
    suspend fun execute(newPassword: String) {
        if (newPassword.isBlank()) {
            throw IllegalArgumentException("Password cannot be empty")
        }
        passwordValidator.save(newPassword)
    }
}
