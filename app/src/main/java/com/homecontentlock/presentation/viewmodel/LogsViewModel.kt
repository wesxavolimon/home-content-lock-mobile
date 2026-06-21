package com.homecontentlock.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homecontentlock.domain.model.LogEntry
import com.homecontentlock.usecase.QueryLogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(
    private val queryLogsUseCase: QueryLogsUseCase
) : ViewModel() {

    val logs: StateFlow<List<LogEntry>> = queryLogsUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
}
