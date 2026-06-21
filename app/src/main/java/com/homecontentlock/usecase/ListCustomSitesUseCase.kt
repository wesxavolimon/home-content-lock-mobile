package com.homecontentlock.usecase

import com.homecontentlock.domain.model.CustomBlockedSite
import com.homecontentlock.domain.repository.IBlockerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListCustomSitesUseCase @Inject constructor(
    private val repository: IBlockerRepository
) {
    operator fun invoke(): Flow<List<CustomBlockedSite>> {
        return repository.getCustomSites()
    }
}
