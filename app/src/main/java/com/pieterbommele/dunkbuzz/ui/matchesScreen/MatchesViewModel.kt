package com.pieterbommele.dunkbuzz.ui.matchesScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pieterbommele.dunkbuzz.DunkBuzzApplication
import com.pieterbommele.dunkbuzz.data.MatchesRepository
import com.pieterbommele.dunkbuzz.data.TeamsRepository
import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamApiState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamListState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverviewState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.WorkerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MatchesViewModel(private val matchesRepository: MatchesRepository) : ViewModel() {
    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate> = _selectedDate
    // use StateFlow (Flow: emits current state + any updates)
    /*
    * Note: uiState is a cold flow. Changes don't come in from above unless a
    * refresh is called...
    * */
    private val _uiState = MutableStateFlow(MatchOverviewState(/*TaskSampler.getAll()*/))
    val uiState: StateFlow<MatchOverviewState> = _uiState.asStateFlow()

    /*
    * Note: uiListState is a hot flow (.stateIn makes it so) --> it updates given a scope (viewmodelscope)
    * when no updates are required (lifecycle) the subscription is stopped after a timeout
    * */
    lateinit var uiListState: StateFlow<MatchListState>

    // keeping the state of the api request
    var matchApiState: MatchApiState by mutableStateOf(MatchApiState.Loading)
        private set

    // state of the workers, prepared here for the UI
    //note, a better approach would use a new data class to represent the state...
    lateinit var wifiWorkerState: StateFlow<WorkerState>
    init {
        onDateSelected(LocalDate.now())
    }
    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date

        // Directly create a ZonedDateTime at the start of the day (00:00) for the given date in UTC
        val startOfDayUTC = date.atStartOfDay(ZoneOffset.UTC)

        // Define a custom formatter that includes milliseconds
        val customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        // Format the ZonedDateTime as a String in the custom format with milliseconds
        val formattedDate = customFormatter.format(startOfDayUTC)

        // Now formattedDate is like "2023-01-01T00:00:00.000Z"
        getRepoMatches(formattedDate)
    }



        // this
    private fun getRepoMatches(date: String) {
        try {

            viewModelScope.launch {
              matchesRepository.refresh(date = date)
            }
                uiListState = matchesRepository.getMatches(date = date).map { MatchListState(it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = MatchListState(),
                    )
                matchApiState = MatchApiState.Success

            wifiWorkerState = matchesRepository.wifiWorkInfo.map { WorkerState(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = WorkerState(),
            )
        } catch (e: IOException) {
            // show a toast? save a log on firebase? ...
            // set the error state
            matchApiState = MatchApiState.Error
        }
    }


    // object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        private var Instance: MatchesViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DunkBuzzApplication)
                    val matchesRepository = application.container.matchesRepository
                    Instance = MatchesViewModel(matchesRepository = matchesRepository)
                }
                Instance!!
            }
        }
    }

}