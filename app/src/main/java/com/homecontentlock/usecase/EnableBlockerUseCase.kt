package com.homecontentlock.usecase

import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.model.LogEntry
import com.homecontentlock.domain.repository.IBlockerRepository
import com.homecontentlock.domain.repository.IPasswordValidator
import java.time.Instant
import javax.inject.Inject

class EnableBlockerUseCase @Inject constructor(
    private val repository: IBlockerRepository,
    private val passwordValidator: IPasswordValidator
) {
    suspend fun execute(password: String = "") {
        // Validate password if one is set
        if (password.isNotEmpty()) {
            passwordValidator.validate(password)
        }

        // Set status to enabled
        repository.setBlockerStatus(BlockerStatus.Enabled)

        // Log the action
        val logEntry = LogEntry(
            id = 0,
            timestamp = Instant.now(),
            action = "ENABLE",
            status = BlockerStatus.Enabled,
            details = null
        )
        repository.addLogEntry(logEntry)
    }
}
