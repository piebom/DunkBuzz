package com.pieterbommele.repository

import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.network.Team.asDomainObjects
import com.pieterbommele.fake.FakeApiTeamsRepository
import com.pieterbommele.fake.FakeApiTeamsService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ApiTeamRepositoryTest {
    @Test
    fun VerifyGetTeams() {
        runTest {
            val repositoryTest = FakeApiTeamsRepository().getTeams()
            var repositoryTeams: List<Team> = emptyList()
            repositoryTest.collect { teams ->
                repositoryTeams = repositoryTeams.plus(teams)
            }
            assertEquals(FakeApiTeamsService().getTeams().data.asDomainObjects(), repositoryTeams)
        }
    }
}