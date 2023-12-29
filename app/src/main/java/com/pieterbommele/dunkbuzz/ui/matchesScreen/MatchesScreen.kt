package com.pieterbommele.dunkbuzz.ui.matchesScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pieterbommele.dunkbuzz.R
import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.ui.components.MatchItem
import com.pieterbommele.dunkbuzz.ui.components.TeamItem
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamApiState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamListState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverviewState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverviewViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MatchesScreen(modifier: Modifier = Modifier,
                  matchesViewModel: MatchesViewModel = viewModel(factory = MatchesViewModel.Factory),
                  isAddingVisisble: Boolean = false,
                  makeInvisible: () -> Unit = {},
) {
    Log.i("vm inspection", "TaskOverview composition")
    val taskOverviewState by matchesViewModel.uiState.collectAsState()
    val taskListState by matchesViewModel.uiListState.collectAsState()
    val currentDate = LocalDate.now()
    val dateItems = remember { generateDateItems(currentDate) }
    val datePickerLazyListState = rememberLazyListState()
    val selectedDateState = remember { mutableStateOf<LocalDate?>(currentDate) }
    val coroutineScope = rememberCoroutineScope()
    // use the ApiState
    val taskApiState = matchesViewModel.matchApiState

    //use the workerstate
    val workerState by matchesViewModel.wifiWorkerState.collectAsState()
    LaunchedEffect(selectedDateState.value) {
        selectedDateState.value?.let { selectedDate ->
            // Calculate the index to scroll to
            val indexToScrollTo = dateItems.indexOfFirst { it.dayOfMonth == selectedDate.dayOfMonth }
            if (indexToScrollTo != -1) {
                coroutineScope.launch {
                    // Scroll to the index of the selected date
                    datePickerLazyListState.animateScrollToItem(indexToScrollTo,-40)
                }
            }
        }

    }


    Column (modifier = modifier.fillMaxSize()) {
        val monthYearText = remember(selectedDateState.value) {
            "${selectedDateState.value?.month?.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${selectedDateState.value?.year}"
        }
        Text(
            text = monthYearText,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = dimensionResource(R.dimen.smallSpacer)),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        DatePickerRow(
            dateItems = dateItems,
            selectedDate = selectedDateState.value,
            onDateSelected = { dateItem ->
                selectedDateState.value = LocalDate.of(currentDate.year, currentDate.month, dateItem.dayOfMonth)
                matchesViewModel.onDateSelected(selectedDateState.value!!)
            },
            lazyListState = datePickerLazyListState
        )

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

data class DateItem(
    val id: Long,  // Unique identifier, can be the epoch day or something similar
    val dayOfWeek: String,
    val dayOfMonth: Int
)

fun generateDateItems(selectedDate: LocalDate): List<DateItem> {
    val items = mutableListOf<DateItem>()
    val startOfMonth = selectedDate.withDayOfMonth(1)
    val endOfMonth = selectedDate.withDayOfMonth(selectedDate.lengthOfMonth())

    var currentDate = startOfMonth
    while (currentDate.isBefore(endOfMonth) || currentDate.isEqual(endOfMonth)) {
        items.add(
            DateItem(
                id = currentDate.toEpochDay(),  // Example identifier
                dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                dayOfMonth = currentDate.dayOfMonth
            )
        )
        currentDate = currentDate.plusDays(1)
    }

    return items
}

@Composable
fun DatePickerRow(
    dateItems: List<DateItem>,
    selectedDate: LocalDate?,
    onDateSelected: (DateItem) -> Unit,
    lazyListState: LazyListState
) {
    LazyRow(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
        items(dateItems) { dateItem ->
            val isSelected = selectedDate?.dayOfMonth == dateItem.dayOfMonth  // Determine if this item is selected
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // circle box
                Box(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .size(dimensionResource(R.dimen.largePadding)) // Set a fixed size for the circle
                        .clip(CircleShape) // Clip the box to a circle
                        .border(
                            dimensionResource(R.dimen.border),
                            if (isSelected) MaterialTheme.colorScheme.onSecondary else Color.Transparent,
                            CircleShape
                        )
                        .background(if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary)  // Light gray for selected, transparent for not selected
                        .clickable { onDateSelected(dateItem) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dateItem.dayOfMonth.toString(),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray, // Black text for selected, gray for not selected
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal // Bold for selected, normal for not selected
                    )
                }
                Text(
                    text = dateItem.dayOfWeek,
                    color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.tertiary, // Black text for selected, gray for not selected
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal // Bold for selected, normal for not selected
                )
            }
        }
    }
}


@Composable
fun TeamListComponent(modifier: Modifier = Modifier, matchOverviewState: MatchOverviewState, matchListState: MatchListState) {
    val lazyListState = rememberLazyListState()
    if(matchListState.matchList.isNotEmpty()) {
        LazyColumn(
            state = lazyListState, modifier = modifier
                .padding(top = dimensionResource(R.dimen.smallSpacer))
        ) {
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