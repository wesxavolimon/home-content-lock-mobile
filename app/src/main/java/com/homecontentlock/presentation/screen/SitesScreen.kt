package com.homecontentlock.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.homecontentlock.domain.model.CustomBlockedSite
import com.homecontentlock.presentation.viewmodel.SitesViewModel

@Composable
fun SitesScreen(viewModel: SitesViewModel = hiltViewModel()) {
    val sites by viewModel.sites.collectAsState(initial = emptyList())
    var newSiteDomain by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Custom Blocked Sites",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = newSiteDomain,
                    onValueChange = { newSiteDomain = it },
                    label = { Text("Domain (e.g., example.com)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (newSiteDomain.isNotBlank()) {
                            viewModel.addSite(newSiteDomain)
                            newSiteDomain = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Site")
                }
            }
        }

        if (sites.isEmpty()) {
            Text("No blocked sites configured", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sites) { site ->
                    SiteListItem(site, onRemove = { viewModel.removeSite(site.domain) })
                }
            }
        }
    }
}

@Composable
fun SiteListItem(site: CustomBlockedSite, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = site.domain,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Active: ${site.isActive}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = onRemove,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Remove")
            }
        }
    }
}
