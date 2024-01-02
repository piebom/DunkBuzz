package com.pieterbommele.fake

import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.network.Match.ApiMatch
import com.pieterbommele.dunkbuzz.network.Team.ApiTeam

object FakeDataSource {
    val teamsApi = listOf(
        ApiTeam(1, "ATL", "Atlanta", "East", "Southeast", "Atlanta Hawks", "Hawks"),
        ApiTeam(2, "BOS", "Boston", "East", "Atlantic", "Boston Celtics", "Celtics"),
        ApiTeam(3, "BKN", "Brooklyn", "East", "Atlantic", "Brooklyn Nets", "Nets"),
    )
    val teams = listOf(
        Team(1, "ATL", "Atlanta", "East", "Southeast", "Atlanta Hawks", "Hawks"),
        Team(2, "BOS", "Boston", "East", "Atlantic", "Boston Celtics", "Celtics"),
        Team(3, "BKN", "Brooklyn", "East", "Atlantic", "Brooklyn Nets", "Nets"),
    )
    val matches = listOf(
        Match(1, "2023-12-23T00:00:00.000Z", teams[0], 95, 4, false, 2023, "Final", "Final", teams[1], 101),
        Match(2, "2023-12-23T00:00:00.000Z", teams[2], 95, 4, false, 2023, "Final", "Final", teams[1], 101),
        Match(3, "2023-12-23T00:00:00.000Z", teams[0], 95, 4, false, 2023, "Final", "Final", teams[2], 101),
    )
    val matchesApi = listOf(
        ApiMatch(1, "2023-12-23T00:00:00.000Z", teamsApi[0], 95, 4, false, 2023, "Final", "Final", teamsApi[1], 101),
        ApiMatch(2, "2023-12-23T00:00:00.000Z", teamsApi[2], 95, 4, false, 2023, "Final", "Final", teamsApi[1], 101),
        ApiMatch(3, "2023-12-23T00:00:00.000Z", teamsApi[0], 95, 4, false, 2023, "Final", "Final", teamsApi[2], 101),
    )
}
