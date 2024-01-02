package com.pieterbommele

import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamApiState
import com.pieterbommele.dunkbuzz.ui.overviewScreen.TeamOverviewViewModel
import com.pieterbommele.fake.FakeApiTeamsRepository
import com.pieterbommele.fake.FakeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher

class TeamOverviewViewModelTest {
    private lateinit var viewModel: TeamOverviewViewModel
    private lateinit var apiState: TeamApiState

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Before
    fun init() {
        viewModel = TeamOverviewViewModel(
            teamsRepository = FakeApiTeamsRepository()
        )

        apiState = viewModel.teamApiState
        when (apiState) {
            is TeamApiState.Success -> return
            else -> {
                throw AssertionError("ViewModel not in success state")
            }
        }
    }

    @Test
    fun getTeamsTest() = runTest {
        Assert.assertEquals(FakeDataSource.teams, getAllTeams())
    }

    suspend fun getAllTeams(): List<Team> {
        val thisTeams: List<Team>
        when (apiState) {
            is TeamApiState.Success -> {
                thisTeams = viewModel.uiListState.first().teamList
            }

            else -> {
                throw AssertionError("ViewModel not in success state")
            }
        }
        return thisTeams
    }
}

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: org.junit.runner.Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: org.junit.runner.Description?) {
        Dispatchers.resetMain()
    }
}
