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
import com.pieterbommele.dunkbuzz.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
class TeamOverviewViewModel(private val teamsRepository: TeamsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TeamOverviewState(/*TaskSampler.getAll()*/))
    val uiState: StateFlow<TeamOverviewState> = _uiState.asStateFlow()

    /*
    * Note: uiListState is a hot flow (.stateIn makes it so) --> it updates given a scope (viewmodelscope)
    * when no updates are required (lifecycle) the subscription is stopped after a timeout
    * */
    lateinit var uiListState: StateFlow<TeamListState>

    // keeping the state of the api request
    var teamApiState: TeamApiState by mutableStateOf(TeamApiState.Loading)
        private set

    // state of the workers, prepared here for the UI
    //note, a better approach would use a new data class to represent the state...
    lateinit var wifiWorkerState: StateFlow<WorkerState>

    init {

        // initializes the uiListState
        getRepoTeams()
        Log.i("vm inspection", "TaskOverviewViewModel init")



    }

    fun addTask() {
        // saving the new task (to db? to network? --> doesn't matter)
        viewModelScope.launch { saveTask(Team(0,_uiState.value.newAbbreviation, _uiState.value.newCity,_uiState.value.newConference,_uiState.value.newDivision,_uiState.value.newFull_name,_uiState.value.newName)) }

        // reset the input fields
        _uiState.update {
                currentState ->
            currentState.copy(
                /*currentTaskList = currentState.currentTaskList +
                    Task(currentState.newTaskName, currentState.newTaskDescription),*/
                // clean up previous values
                newAbbreviation = "",
                newCity = "",
                newConference = "",
                newDivision = "",
                newFull_name = "",
                newName = "",
                // whenever this changes, scrollToItemIndex should be scrolled into view
                scrollActionIdx = currentState.scrollActionIdx.plus(1),
                scrollToItemIndex = uiListState.value.teamList.size,
            )
        }
    }
    private fun validateInput(): Boolean {
        return with(_uiState) {
            value.newAbbreviation.length > 0 && value.newCity.length > 0 && value.newConference.length > 0 && value.newDivision.length > 0 && value.newFull_name.length > 0 && value.newName.length > 0
        }
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

            wifiWorkerState = teamsRepository.wifiWorkInfo.map { WorkerState(it)}.stateIn(
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

    private suspend fun saveTask(team: Team) {
        if (validateInput()) {
            teamsRepository.insertTeam(team)
        }
    }

    fun onVisibilityChanged() {
        _uiState.update {
            it.copy(isAddingVisible = !_uiState.value.isAddingVisible)
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