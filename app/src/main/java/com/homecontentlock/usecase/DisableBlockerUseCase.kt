package com.homecontentlock.usecase

import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.model.LogEntry
import com.homecontentlock.domain.repository.IBlockerRepository
import com.homecontentlock.domain.repository.IPasswordValidator
import com.homecontentlock.domain.exception.InvalidPasswordException
import java.time.Instant
import javax.inject.Inject

class DisableBlockerUseCase @Inject constructor(
    private val repository: IBlockerRepository,
    private val passwordValidator: IPasswordValidator
) {
    suspend fun execute(password: String) {
        // Validate password
        val isValid = passwordValidator.validate(password)
        if (!isValid) {
            throw InvalidPasswordException("Invalid password")
        }

        // Set status to disabled
        repository.setBlockerStatus(BlockerStatus.Disabled)

        // Log the action
        val logEntry = LogEntry(
            id = 0,
            timestamp = Instant.now(),
            action = "DISABLE",
            status = BlockerStatus.Disabled,
            details = null
        )
        repository.addLogEntry(logEntry)
    }
}
