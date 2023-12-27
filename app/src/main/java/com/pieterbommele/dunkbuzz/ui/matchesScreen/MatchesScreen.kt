package com.pieterbommele.dunkbuzz.ui.matchesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun MatchesScreen(vm: MatchesViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "about", modifier = Modifier.align(Alignment.Center))
    }
}
