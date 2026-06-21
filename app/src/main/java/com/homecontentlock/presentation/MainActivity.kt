package com.homecontentlock.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.homecontentlock.presentation.nav.NavGraph
import com.homecontentlock.presentation.nav.NavRoute
import com.homecontentlock.presentation.screen.LogsScreen
import com.homecontentlock.presentation.screen.PasswordScreen
import com.homecontentlock.presentation.screen.SitesScreen
import com.homecontentlock.presentation.screen.StatusScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var selectedTab by remember { mutableIntStateOf(0) }
                    val tabs = listOf("Status", "Logs", "Password", "Sites")

                    Column {
                        TabRow(selectedTabIndex = selectedTab) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    text = { Text(title) }
                                )
                            }
                        }

                        when (selectedTab) {
                            0 -> StatusScreen()
                            1 -> LogsScreen()
                            2 -> PasswordScreen()
                            3 -> SitesScreen()
                        }
                    }
                }
            }
        }
    }
}
