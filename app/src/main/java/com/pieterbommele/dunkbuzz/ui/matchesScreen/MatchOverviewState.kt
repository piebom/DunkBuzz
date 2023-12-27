package com.pieterbommele.dunkbuzz.ui.matchesScreen

import androidx.work.WorkInfo
import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.model.Team

data class MatchOverviewState (
    val isAddingVisible: Boolean = false,
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
    )

    data class MatchListState(val matchList: List<Match> = listOf())

    data class WorkerState(val workerInfo: WorkInfo? = null)

// the sealed interface has only three possible values
    /*Sidenote: to learn more about this TaskApiState object, you can search on LCE (Loading, Content, Error) pattern

    When the state is changed to Error, the taskList will not be updated (offline first).
    To ensure the list is considered immutable (fully immutable, won't ever change unless a new object is created), add the Immutable annotation.

    The LCE pattern is not completed in the application, because it requires more complex helper classes
    An example can be found here https://www.valueof.io/blog/compose-ui-state-flow-offline-first-repository
    */

    sealed interface MatchApiState {
        object Success : MatchApiState
        object Error : MatchApiState
        object Loading : MatchApiState
    }