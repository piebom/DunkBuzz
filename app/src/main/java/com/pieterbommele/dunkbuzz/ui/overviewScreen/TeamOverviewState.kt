package com.pieterbommele.dunkbuzz.ui.overviewScreen

import androidx.work.WorkInfo
import com.pieterbommele.dunkbuzz.model.Team

data class TeamOverviewState (
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

    data class TeamListState(val teamList: List<Team> = listOf())

    data class WorkerState(val workerInfo: WorkInfo? = null)

// the sealed interface has only three possible values
    /*Sidenote: to learn more about this TaskApiState object, you can search on LCE (Loading, Content, Error) pattern

    When the state is changed to Error, the taskList will not be updated (offline first).
    To ensure the list is considered immutable (fully immutable, won't ever change unless a new object is created), add the Immutable annotation.

    The LCE pattern is not completed in the application, because it requires more complex helper classes
    An example can be found here https://www.valueof.io/blog/compose-ui-state-flow-offline-first-repository
    */

    sealed interface TeamApiState {
        object Success : TeamApiState
        object Error : TeamApiState
        object Loading : TeamApiState
    }