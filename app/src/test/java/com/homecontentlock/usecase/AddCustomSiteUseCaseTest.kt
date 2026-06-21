package com.homecontentlock.usecase

import com.homecontentlock.domain.repository.IBlockerRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AddCustomSiteUseCaseTest {

    private lateinit var mockRepository: IBlockerRepository
    private lateinit var useCase: AddCustomSiteUseCase

    @Before
    fun setup() {
        mockRepository = mock()
        useCase = AddCustomSiteUseCase(mockRepository)
    }

    @Test
    fun execute_addsSiteToRepository() = runTest {
        useCase.execute("example.com")

        verify(mockRepository).addCustomSite(any())
    }

    @Test(expected = IllegalArgumentException::class)
    fun execute_withEmptyDomain_throwsException() = runTest {
        useCase.execute("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun execute_withBlankDomain_throwsException() = runTest {
        useCase.execute("   ")
    }
}
