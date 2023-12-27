package com.pieterbommele.dunkbuzz.network.Match

import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.network.ApiMeta
import com.pieterbommele.dunkbuzz.network.Team.ApiTeam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseMatch(
    val data: List<ApiMatch>,
    val meta: ApiMeta
)

@Serializable
data class ApiMatch(
    val id: Int,
    val date: String?,
    val home_team: ApiTeam,
    val home_team_score: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String?,
    val visitor_team: ApiTeam,
    val visitor_team_score: Int
)

// extension function for an ApiTask List to convert is to a Domain Task List
fun Flow<List<ApiMatch>>.asDomainObjects(): Flow<List<Match>> {
    return map {
        it.asDomainObjects()
    }
}

fun List<ApiMatch>.asDomainObjects(): List<Match> {
    var domainList = this.map {
        Match(
            id = it.id,
            date = it.date ?: "",
            homeTeam = Team(
                id = it.home_team.id,
                abbreviation = it.home_team.abbreviation,
                city = it.home_team.city,
                conference = it.home_team.conference,
                division = it.home_team.division,
                full_name = it.home_team.full_name,
                name = it.home_team.name
            ),
            homeTeamScore = it.home_team_score,
            period = it.period,
            postseason = it.postseason,
            season = it.season,
            status = it.status,
            time = it.time ?: "",
            visitorTeam = Team(
                id = it.visitor_team.id,
                abbreviation = it.visitor_team.abbreviation,
                city = it.visitor_team.city,
                conference = it.visitor_team.conference,
                division = it.visitor_team.division,
                full_name = it.visitor_team.full_name,
                name = it.visitor_team.name
            ),
            visitorTeamScore = it.visitor_team_score
        )
    }
    return domainList
}