package com.homecontentlock.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.model.LogEntry
import com.homecontentlock.usecase.DisableBlockerUseCase
import com.homecontentlock.usecase.EnableBlockerUseCase
import com.homecontentlock.usecase.GetStatusUseCase
import com.homecontentlock.usecase.QueryLogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val getStatusUseCase: GetStatusUseCase,
    private val enableBlockerUseCase: EnableBlockerUseCase,
    private val disableBlockerUseCase: DisableBlockerUseCase,
    private val queryLogsUseCase: QueryLogsUseCase
) : ViewModel() {

    val status: StateFlow<BlockerStatus> = getStatusUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            BlockerStatus.Disabled
        )

    val logCount: StateFlow<Int> = queryLogsUseCase()
        .map { it.size }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            0
        )

    val recentLogs: StateFlow<List<LogEntry>> = queryLogsUseCase()
        .map { it.take(5) }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun enable() {
        viewModelScope.launch {
            try {
                enableBlockerUseCase.execute()
            } catch (e: Exception) {
                Timber.e(e, "Failed to enable blocker")
            }
        }
    }

    fun disable(password: String) {
        viewModelScope.launch {
            try {
                disableBlockerUseCase.execute(password)
            } catch (e: Exception) {
                Timber.e(e, "Failed to disable blocker")
            }
        }
    }
}
