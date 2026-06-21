package com.homecontentlock.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homecontentlock.usecase.SetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PasswordState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val setPasswordUseCase: SetPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PasswordState())
    val state: StateFlow<PasswordState> = _state

    fun updateCurrentPassword(password: String) {
        _state.value = _state.value.copy(currentPassword = password)
    }

    fun updateNewPassword(password: String) {
        _state.value = _state.value.copy(newPassword = password)
    }

    fun updateConfirmPassword(password: String) {
        _state.value = _state.value.copy(confirmPassword = password)
    }

    fun setPassword() {
        viewModelScope.launch {
            try {
                val newPass = _state.value.newPassword
                val confirmPass = _state.value.confirmPassword

                if (newPass.isBlank()) {
                    _state.value = _state.value.copy(errorMessage = "Password cannot be empty")
                    return@launch
                }

                if (newPass != confirmPass) {
                    _state.value = _state.value.copy(errorMessage = "Passwords do not match")
                    return@launch
                }

                _state.value = _state.value.copy(isLoading = true, errorMessage = null)
                setPasswordUseCase.execute(newPass)
                _state.value = _state.value.copy(
                    isLoading = false,
                    successMessage = "Password updated successfully",
                    currentPassword = "",
                    newPassword = "",
                    confirmPassword = ""
                )
            } catch (e: Exception) {
                Timber.e(e, "Failed to set password")
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to update password"
                )
            }
        }
    }

    fun clearMessages() {
        _state.value = _state.value.copy(errorMessage = null, successMessage = null)
    }
}
