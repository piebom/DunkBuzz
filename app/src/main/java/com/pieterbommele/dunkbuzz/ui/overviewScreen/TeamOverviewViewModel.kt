package com.pieterbommele.dunkbuzz.ui.overviewScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pieterbommele.dunkbuzz.DunkBuzzApplication
import com.pieterbommele.dunkbuzz.data.TeamsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel responsible for managing the state of the Team Overview screen.
 *
 * @property teamsRepository The repository responsible for fetching and storing team data.
 */
class TeamOverviewViewModel(private val teamsRepository: TeamsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TeamOverviewState(/*TaskSampler.getAll()*/))
    val uiState: StateFlow<TeamOverviewState> = _uiState.asStateFlow()

    lateinit var uiListState: StateFlow<TeamListState>

    // keeping the state of the api request
    var teamApiState: TeamApiState by mutableStateOf(TeamApiState.Loading)
        private set

    // state of the workers, prepared here for the UI
    // note, a better approach would use a new data class to represent the state...
    lateinit var wifiWorkerState: StateFlow<WorkerState>

    init {

        // initializes the uiListState
        getRepoTeams()
           }

    // this
    private fun getRepoTeams() {
        try {
            viewModelScope.launch { teamsRepository.refresh() }

            uiListState = teamsRepository.getTeams().map { TeamListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = TeamListState(),
                )
            teamApiState = TeamApiState.Success

            wifiWorkerState = teamsRepository.wifiWorkInfo.map { WorkerState(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = WorkerState(),
            )
        } catch (e: IOException) {
            // show a toast? save a log on firebase? ...
            // set the error state
            teamApiState = TeamApiState.Error
        }
    }

    // object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        private var Instance: TeamOverviewViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[APPLICATION_KEY] as DunkBuzzApplication)
                    val teamsRepository = application.container.teamsRepository
                    Instance = TeamOverviewViewModel(teamsRepository = teamsRepository)
                }
                Instance!!
            }
        }
    }
}
