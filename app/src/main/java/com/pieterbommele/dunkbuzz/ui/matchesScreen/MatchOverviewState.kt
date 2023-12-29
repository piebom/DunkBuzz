package com.pieterbommele.dunkbuzz.ui.matchesScreen

import androidx.work.WorkInfo
import com.pieterbommele.dunkbuzz.model.Match

/**
 * Represents the state for the Match Overview screen.
 *
 * @param isAddingVisible Indicates whether the add match view is visible.
 * @param scrollActionIdx Index for scroll action.
 * @param scrollToItemIndex Index to scroll to in the match list.
 */
data class MatchOverviewState(
    val isAddingVisible: Boolean = false,
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)

/**
 * Represents the state for the list of matches.
 *
 * @param matchList The list of matches.
 */
data class MatchListState(val matchList: List<Match> = listOf())

/**
 * Represents the state for a worker (background task) related to matches.
 *
 * @param workerInfo The information about the worker's status.
 */
data class WorkerState(val workerInfo: WorkInfo? = null)

/**
 * Sealed interface representing the state of match-related API calls.
 */
sealed interface MatchApiState {
    /**
     * Represents a successful API call.
     */
    object Success : MatchApiState
    /**
     * Represents an error in the API call.
     */
    object Error : MatchApiState
    /**
     * Represents the loading state of the API call.
     */
    object Loading : MatchApiState
}
