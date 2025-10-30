package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.challengewtcc.data.MockRepository

class CampaignViewModel: ViewModel() {
    val campaigns = MockRepository.campaigns
}