package com.homecontentlock.usecase

import com.homecontentlock.domain.model.LogEntry
import com.homecontentlock.domain.repository.IBlockerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QueryLogsUseCase @Inject constructor(
    private val repository: IBlockerRepository
) {
    operator fun invoke(): Flow<List<LogEntry>> {
        return repository.getAllLogEntries()
    }
}
