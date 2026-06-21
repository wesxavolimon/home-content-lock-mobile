package com.homecontentlock.usecase

import com.homecontentlock.domain.exception.InvalidPasswordException
import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.repository.IBlockerRepository
import com.homecontentlock.domain.repository.IPasswordValidator
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DisableBlockerUseCaseTest {

    private lateinit var mockRepository: IBlockerRepository
    private lateinit var mockPasswordValidator: IPasswordValidator
    private lateinit var useCase: DisableBlockerUseCase

    @Before
    fun setup() {
        mockRepository = mock()
        mockPasswordValidator = mock()
        useCase = DisableBlockerUseCase(mockRepository, mockPasswordValidator)
    }

    @Test
    fun execute_validPassword_setsStatusToDisabled() = runTest {
        whenever(mockPasswordValidator.validate("password123")).thenReturn(true)

        useCase.execute("password123")

        verify(mockRepository).setBlockerStatus(BlockerStatus.Disabled)
    }

    @Test
    fun execute_validPassword_logsAction() = runTest {
        whenever(mockPasswordValidator.validate("password123")).thenReturn(true)

        useCase.execute("password123")

        verify(mockRepository).addLogEntry(any())
    }

    @Test(expected = InvalidPasswordException::class)
    fun execute_invalidPassword_throwsException() = runTest {
        whenever(mockPasswordValidator.validate("wrongpassword")).thenReturn(false)

        useCase.execute("wrongpassword")
    }
}
