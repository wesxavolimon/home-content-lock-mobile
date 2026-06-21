package com.homecontentlock.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.presentation.viewmodel.StatusViewModel
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class StatusScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun statusScreen_displaysCurrentStatus() {
        val mockViewModel = mock<StatusViewModel>().apply {
            whenever(status).thenReturn(flowOf(BlockerStatus.Enabled).stateIn(mock(), mock(), BlockerStatus.Disabled))
            whenever(logCount).thenReturn(flowOf(42).stateIn(mock(), mock(), 0))
            whenever(recentLogs).thenReturn(flowOf(emptyList()).stateIn(mock(), mock(), emptyList()))
        }

        composeTestRule.setContent {
            StatusScreen(mockViewModel)
        }

        composeTestRule
            .onNodeWithText("Content Blocker Status")
            .assertIsDisplayed()
    }

    @Test
    fun statusScreen_hasEnableButton() {
        val mockViewModel = mock<StatusViewModel>().apply {
            whenever(status).thenReturn(flowOf(BlockerStatus.Disabled).stateIn(mock(), mock(), BlockerStatus.Disabled))
            whenever(logCount).thenReturn(flowOf(0).stateIn(mock(), mock(), 0))
            whenever(recentLogs).thenReturn(flowOf(emptyList()).stateIn(mock(), mock(), emptyList()))
        }

        composeTestRule.setContent {
            StatusScreen(mockViewModel)
        }

        composeTestRule
            .onNodeWithText("Enable")
            .assertIsDisplayed()
    }

    @Test
    fun statusScreen_hasDisableButton() {
        val mockViewModel = mock<StatusViewModel>().apply {
            whenever(status).thenReturn(flowOf(BlockerStatus.Enabled).stateIn(mock(), mock(), BlockerStatus.Disabled))
            whenever(logCount).thenReturn(flowOf(0).stateIn(mock(), mock(), 0))
            whenever(recentLogs).thenReturn(flowOf(emptyList()).stateIn(mock(), mock(), emptyList()))
        }

        composeTestRule.setContent {
            StatusScreen(mockViewModel)
        }

        composeTestRule
            .onNodeWithText("Disable")
            .assertIsDisplayed()
    }
}
