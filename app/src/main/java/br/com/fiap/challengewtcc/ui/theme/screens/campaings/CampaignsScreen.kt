package br.com.fiap.challengewtcc.ui.theme.screens.campaings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.fiap.challengewtcc.data.remote.request.CreateCampaignRequest
import br.com.fiap.challengewtcc.viewmodel.CampaignViewModel

@Composable
fun CampaignsScreen(
    viewModel: CampaignViewModel
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.loadCampaigns()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text("Campaign title")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text("Description")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {

            Button(
                onClick = {

                    viewModel.createCampaign(
                        CreateCampaignRequest(
                            title = title,
                            description = description,
                            status = "ACTIVE",
                            targetAudience = "ALL",
                            startDate = "2026-05-17",
                            endDate = "2026-12-31"
                        )
                    )
                }
            ) {
                Text("Create")
            }

            TextButton(
                onClick = {
                    viewModel.loadCampaigns()
                }
            ) {
                Text("Refresh")
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(state.campaigns) { campaign ->

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = campaign.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = campaign.description,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}