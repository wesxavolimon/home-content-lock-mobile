package com.homecontentlock.usecase

import com.homecontentlock.domain.model.CustomBlockedSite
import com.homecontentlock.domain.repository.IBlockerRepository
import java.time.Instant
import javax.inject.Inject

class AddCustomSiteUseCase @Inject constructor(
    private val repository: IBlockerRepository
) {
    suspend fun execute(domain: String) {
        if (domain.isBlank()) {
            throw IllegalArgumentException("Domain cannot be empty")
        }

        val site = CustomBlockedSite(
            id = 0,
            domain = domain,
            addedAt = Instant.now(),
            isActive = true
        )
        repository.addCustomSite(site)
    }
}
