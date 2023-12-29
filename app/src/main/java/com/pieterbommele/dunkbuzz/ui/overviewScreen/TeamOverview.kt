package com.pieterbommele.dunkbuzz.ui.overviewScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pieterbommele.dunkbuzz.R
import com.pieterbommele.dunkbuzz.ui.components.TeamItem
import kotlinx.coroutines.launch

/**
 * Composable function for displaying the team overview screen.
 *
 * @param modifier The modifier for the TeamOverview composable.
 * @param teamOverviewViewModel The view model for managing team overview data.
 * @param isAddingVisisble Whether the add action is visible.
 * @param makeInvisible The action to make the add action invisible.
 */
@Composable
fun TeamOverview(
    modifier: Modifier = Modifier,
    teamOverviewViewModel: TeamOverviewViewModel = viewModel(factory = TeamOverviewViewModel.Factory),
    isAddingVisisble: Boolean = false,
    makeInvisible: () -> Unit = {},
) {
    Log.i("vm inspection", "TaskOverview composition")
    val taskOverviewState by teamOverviewViewModel.uiState.collectAsState()
    val taskListState by teamOverviewViewModel.uiListState.collectAsState()

    // use the ApiState
    val taskApiState = teamOverviewViewModel.teamApiState

    // use the workerstate
    val workerState by teamOverviewViewModel.wifiWorkerState.collectAsState()
    Column {
        Box(modifier = modifier) {
            when (taskApiState) {
                is TeamApiState.Loading -> Text("Loading...")
                is TeamApiState.Error -> Text("Couldn't load...")
                is TeamApiState.Success -> TeamListComponent(teamOverviewState = taskOverviewState, teamListState = taskListState)
            }
        }
    }
}

/**
 * Composable function for displaying the list of teams.
 *
 * @param modifier The modifier for the TeamListComponent composable.
 * @param teamOverviewState The state of the team overview.
 * @param teamListState The state of the team list.
 */
@Composable
fun TeamListComponent(modifier: Modifier = Modifier, teamOverviewState: TeamOverviewState, teamListState: TeamListState) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, modifier = modifier.padding(top = dimensionResource(R.dimen.smallSpacer))) {
        items(teamListState.teamList.size) { index ->
            TeamItem(
                teamName = teamListState.teamList[index].name,
                id = teamListState.teamList[index].id,
                teamAbbreviation = teamListState.teamList[index].abbreviation,
            )
        }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(teamOverviewState.scrollActionIdx) {
        if (teamOverviewState.scrollActionIdx != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(teamOverviewState.scrollToItemIndex)
            }
        }
    }
}
