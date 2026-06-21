package com.homecontentlock.usecase

import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.repository.IBlockerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStatusUseCase @Inject constructor(
    private val repository: IBlockerRepository
) {
    operator fun invoke(): Flow<BlockerStatus> = flow {
        emit(repository.getBlockerStatus())
    }
}
