package com.homecontentlock.usecase

import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.repository.IBlockerRepository
import com.homecontentlock.domain.repository.IPasswordValidator
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class EnableBlockerUseCaseTest {

    private lateinit var mockRepository: IBlockerRepository
    private lateinit var mockPasswordValidator: IPasswordValidator
    private lateinit var useCase: EnableBlockerUseCase

    @Before
    fun setup() {
        mockRepository = mock()
        mockPasswordValidator = mock()
        useCase = EnableBlockerUseCase(mockRepository, mockPasswordValidator)
    }

    @Test
    fun execute_setsStatusToEnabled() = runTest {
        useCase.execute()

        verify(mockRepository).setBlockerStatus(BlockerStatus.Enabled)
    }

    @Test
    fun execute_logsAction() = runTest {
        useCase.execute()

        verify(mockRepository).addLogEntry(any())
    }

    @Test
    fun execute_withPassword_validatesPassword() = runTest {
        useCase.execute("password123")

        verify(mockPasswordValidator).validate("password123")
    }
}
