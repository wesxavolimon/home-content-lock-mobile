package com.homecontentlock.usecase

import com.homecontentlock.domain.repository.IBlockerRepository
import javax.inject.Inject

class RemoveCustomSiteUseCase @Inject constructor(
    private val repository: IBlockerRepository
) {
    suspend fun execute(domain: String) {
        if (domain.isBlank()) {
            throw IllegalArgumentException("Domain cannot be empty")
        }
        repository.removeCustomSite(domain)
    }
}
