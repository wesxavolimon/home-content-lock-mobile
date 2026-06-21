package com.homecontentlock.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homecontentlock.domain.model.CustomBlockedSite
import com.homecontentlock.usecase.AddCustomSiteUseCase
import com.homecontentlock.usecase.ListCustomSitesUseCase
import com.homecontentlock.usecase.RemoveCustomSiteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SitesViewModel @Inject constructor(
    private val listCustomSitesUseCase: ListCustomSitesUseCase,
    private val addCustomSiteUseCase: AddCustomSiteUseCase,
    private val removeCustomSiteUseCase: RemoveCustomSiteUseCase
) : ViewModel() {

    val sites: StateFlow<List<CustomBlockedSite>> = listCustomSitesUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun addSite(domain: String) {
        viewModelScope.launch {
            try {
                addCustomSiteUseCase.execute(domain)
            } catch (e: Exception) {
                Timber.e(e, "Failed to add custom site: $domain")
            }
        }
    }

    fun removeSite(domain: String) {
        viewModelScope.launch {
            try {
                removeCustomSiteUseCase.execute(domain)
            } catch (e: Exception) {
                Timber.e(e, "Failed to remove custom site: $domain")
            }
        }
    }
}
