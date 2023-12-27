package com.pieterbommele.dunkbuzz.ui.matchesScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.ui.components.MatchItem
import com.pieterbommele.dunkbuzz.ui.components.TeamItem
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamApiState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamListState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverviewState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverviewViewModel
import kotlinx.coroutines.launch

@Composable
fun MatchesScreen(modifier: Modifier = Modifier,
                  matchesViewModel: MatchesViewModel = viewModel(factory = MatchesViewModel.Factory),
                  isAddingVisisble: Boolean = false,
                  makeInvisible: () -> Unit = {},
) {
    Log.i("vm inspection", "TaskOverview composition")
    val taskOverviewState by matchesViewModel.uiState.collectAsState()
    val taskListState by matchesViewModel.uiListState.collectAsState()

    // use the ApiState
    val taskApiState = matchesViewModel.matchApiState

    //use the workerstate
    val workerState by matchesViewModel.wifiWorkerState.collectAsState()
    Column {
        Box(modifier = modifier) {
            when (taskApiState) {
                is MatchApiState.Loading -> Text("Loading...")
                is MatchApiState.Error -> Text("Couldn't load...")
                is MatchApiState.Success -> TeamListComponent(matchOverviewState = taskOverviewState, matchListState = taskListState)
            }

//            if (isAddingVisisble) {
//                CreateTask(
//                    taskName = taskOverviewState.newTaskName,
//                    taskDescription = taskOverviewState.newTaskDescription,
//                    onTaskNameChanged = { taskOverviewViewModel.setNewTaskName(it) },
//                    onTaskDescriptionChanged = { taskOverviewViewModel.setNewTaskDescription(it) },
//                    onTaskSaved = {
//                        taskOverviewViewModel.addTask()
//                        makeInvisible()
//                    },
//                    onDismissRequest = { makeInvisible() },
//                )
//            }
        }
    }
}

@Composable
fun TeamListComponent(modifier: Modifier = Modifier, matchOverviewState: MatchOverviewState, matchListState: MatchListState) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, modifier = modifier.background(Color.White).padding(top = 16.dp)) {
        items(matchListState.matchList.size) { index ->
            MatchItem(
                home_team_name = matchListState.matchList[index].homeTeam.name,
                home_team_id = matchListState.matchList[index].homeTeam.id,
                home_team_score = matchListState.matchList[index].homeTeamScore,
                visitor_team_name = matchListState.matchList[index].visitorTeam.name,
                visitor_team_id = matchListState.matchList[index].visitorTeam.id,
                visitor_team_score = matchListState.matchList[index].visitorTeamScore,
                quarter = matchListState.matchList[index].period
            )
        }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(matchOverviewState.scrollActionIdx) {
        if (matchOverviewState.scrollActionIdx != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(matchOverviewState.scrollToItemIndex)
            }
        }
    }
}

//@Preview(showBackground = true, widthDp = 1000)
//@Composable
//fun TaskListComponentPreview() {
//    TeamListComponent(matchOverviewState = MatchOverviewState(), teamListState = TeamListState(listOf(
//        Match(
//            0,
//            "previewtask",
//            "previewdescription",
//            "previewdescription",
//            "previewdescription",
//            "previewdescription",
//            "previewdescription",
//            "previewdescription",
//            "previewdescription",
//            "previewdescription",
//            "previewdescription",
//        )
//    ))
//    )
//}