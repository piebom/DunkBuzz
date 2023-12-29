package com.pieterbommele.dunkbuzz.network.Match

import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.network.ApiMeta
import com.pieterbommele.dunkbuzz.network.Team.ApiTeam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

/**
 * Represents the response structure for match-related API calls.
 *
 * @property data The list of match data retrieved from the API.
 * @property meta Meta-information about the response, like pagination details.
 */
@Serializable
data class ApiResponseMatch(
    val data: List<ApiMatch>,
    val meta: ApiMeta
)

/**
 * Represents match data retrieved from the API.
 *
 * @property id The unique identifier for the match.
 * @property date The date of the match. Nullable.
 * @property home_team The home team data.
 * @property home_team_score The score for the home team.
 * @property period The period in which the match is.
 * @property postseason Boolean indicating if the match is in the postseason.
 * @property season The season year the match is part of.
 * @property status The current status of the match.
 * @property time The time at which the match takes place. Nullable.
 * @property visitor_team The visitor team data.
 * @property visitor_team_score The score for the visitor team.
 */
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

/**
 * Transforms a flow of a list of [ApiMatch] to a flow of a list of [Match].
 *
 * This function maps the data from the API-specific match format to the domain-specific match format,
 * making it suitable for use throughout the application.
 *
 * @return A flow emitting a list of domain model [Match] instances.
 */
fun Flow<List<ApiMatch>>.asDomainObjects(): Flow<List<Match>> {
    return map {
        it.asDomainObjects()
    }
}

/**
 * Converts a list of [ApiMatch] instances to a list of domain model [Match] instances.
 *
 * This function maps each API-specific match to the domain-specific match format,
 * making it suitable for use throughout the application.
 *
 * @return A list of domain model [Match] instances.
 */
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
