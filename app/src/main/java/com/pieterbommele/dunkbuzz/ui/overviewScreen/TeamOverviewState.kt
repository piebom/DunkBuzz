package com.pieterbommele.dunkbuzz.ui.overviewScreen

import androidx.work.WorkInfo
import com.pieterbommele.dunkbuzz.model.Team

/**
 * Represents the state of the Team Overview screen.
 *
 * @param isAddingVisible Whether the "add" action is visible.
 * @param newAbbreviation The new team's abbreviation.
 * @param newCity The new team's city.
 * @param newConference The new team's conference.
 * @param newDivision The new team's division.
 * @param newFull_name The new team's full name.
 * @param newName The new team's name.
 * @param scrollActionIdx The index for scroll action.
 * @param scrollToItemIndex The index to scroll to.
 */
data class TeamOverviewState(
    // val currentTaskList: List<Task>,
    val isAddingVisible: Boolean = false,
    val newAbbreviation: String = "",
    val newCity: String = "",
    val newConference: String = "",
    val newDivision: String = "",
    val newFull_name: String = "",
    val newName: String = "",
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)

/**
 * Represents the state of the team list.
 *
 * @param teamList The list of teams.
 */
data class TeamListState(val teamList: List<Team> = listOf())

/**
 * Represents the state of a worker.
 *
 * @param workerInfo The worker information.
 */
data class WorkerState(val workerInfo: WorkInfo? = null)

/**
 * Sealed interface for the state of Team API.
 */
sealed interface TeamApiState {
    /**
     * Represents the success state of the Team API.
     */
    object Success : TeamApiState

    /**
     * Represents the error state of the Team API.
     */
    object Error : TeamApiState

    /**
     * Represents the loading state of the Team API.
     */
    object Loading : TeamApiState
}
