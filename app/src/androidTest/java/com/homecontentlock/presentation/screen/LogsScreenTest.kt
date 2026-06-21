package com.homecontentlock.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.homecontentlock.domain.model.BlockerStatus
import com.homecontentlock.domain.model.LogEntry
import com.homecontentlock.presentation.viewmodel.LogsViewModel
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.Instant

@RunWith(AndroidJUnit4::class)
class LogsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun logsScreen_displaysTitle() {
        val mockViewModel = mock<LogsViewModel>().apply {
            whenever(logs).thenReturn(flowOf(emptyList()).stateIn(mock(), mock(), emptyList()))
        }

        composeTestRule.setContent {
            LogsScreen(mockViewModel)
        }

        composeTestRule
            .onNodeWithText("Activity Logs")
            .assertIsDisplayed()
    }

    @Test
    fun logsScreen_displaysEmptyMessage() {
        val mockViewModel = mock<LogsViewModel>().apply {
            whenever(logs).thenReturn(flowOf(emptyList()).stateIn(mock(), mock(), emptyList()))
        }

        composeTestRule.setContent {
            LogsScreen(mockViewModel)
        }

        composeTestRule
            .onNodeWithText("No logs yet")
            .assertIsDisplayed()
    }

    @Test
    fun logsScreen_displaysLogEntries() {
        val logEntries = listOf(
            LogEntry(
                id = 1,
                timestamp = Instant.now(),
                action = "ENABLE",
                status = BlockerStatus.Enabled,
                details = null
            )
        )

        val mockViewModel = mock<LogsViewModel>().apply {
            whenever(logs).thenReturn(flowOf(logEntries).stateIn(mock(), mock(), emptyList()))
        }

        composeTestRule.setContent {
            LogsScreen(mockViewModel)
        }

        composeTestRule
            .onNodeWithText("ENABLE")
            .assertIsDisplayed()
    }
}
