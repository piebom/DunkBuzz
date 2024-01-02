package com.pieterbommele.repository

import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.network.Match.asDomainObjects
import com.pieterbommele.dunkbuzz.network.Team.asDomainObjects
import com.pieterbommele.fake.FakeApiMatchesRepository
import com.pieterbommele.fake.FakeApiMatchesService
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ApiMatchRepositoryTest {
    @Test
    fun VerifyGetMatches() {
        runTest {
            val repositoryTest = FakeApiMatchesRepository().getMatches("2023-12-23T00:00:00.000Z")
            var repositoryMatches: List<Match> = emptyList()
            repositoryTest.collect { match ->
                repositoryMatches = repositoryMatches.plus(match)
            }
            TestCase.assertEquals(FakeApiMatchesService().getMatches("2023-12-23T00:00:00.000Z").data.asDomainObjects(), repositoryMatches)
        }
    }
}
