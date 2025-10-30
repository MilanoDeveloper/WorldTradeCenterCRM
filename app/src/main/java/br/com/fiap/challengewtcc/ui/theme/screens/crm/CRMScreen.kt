package br.com.fiap.challengewtcc.ui.theme.screens.crm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import br.com.fiap.challengewtcc.viewmodel.CRMViewModel

@Composable
fun CRMScreen(vm: CRMViewModel) {
    val selected = vm.selected.collectAsState(null).value
    if (selected == null) {
        CRMListView(vm) { vm.selectClient(it) }
    } else {
        ClientDetailView(vm, selected) { vm.clearSelection() }
    }
}
