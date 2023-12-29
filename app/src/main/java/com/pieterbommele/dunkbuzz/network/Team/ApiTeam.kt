package com.pieterbommele.dunkbuzz.network.Team

import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.network.ApiMeta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

/**
 * Represents the response structure for team-related API calls.
 *
 * @property data The list of team data retrieved from the API.
 * @property meta Meta-information about the response, like pagination details.
 */
@Serializable
data class ApiResponseTeam(
    val data: List<ApiTeam>,
    val meta: ApiMeta
)

/**
 * Represents team data retrieved from the API.
 *
 * @property id The unique identifier for the team.
 * @property abbreviation The standard abbreviation used to refer to the team.
 * @property city The city where the team is based.
 * @property conference The conference to which the team belongs.
 * @property division The division within the conference that the team is part of.
 * @property full_name The full, official name of the team.
 * @property name The common name of the team.
 */
@Serializable
data class ApiTeam(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)

/**
 * Transforms a flow of a list of [ApiTeam] to a flow of a list of [Team].
 *
 * This function maps the data from the API-specific team format to the domain-specific team format,
 * making it suitable for use throughout the application.
 *
 * @return A flow emitting a list of domain model [Team] instances.
 */
fun Flow<List<ApiTeam>>.asDomainObjects(): Flow<List<Team>> {
    return map {
        it.asDomainObjects()
    }
}

/**
 * Converts a list of [ApiTeam] instances to a list of domain model [Team] instances.
 *
 * This function maps each API-specific team to the domain-specific team format,
 * making it suitable for use throughout the application.
 *
 * @return A list of domain model [Team] instances.
 */
fun List<ApiTeam>.asDomainObjects(): List<Team> {
    var domainList = this.map {
        Team(
            id = it.id,
            abbreviation = it.abbreviation,
            city = it.city,
            conference = it.conference,
            division = it.division,
            full_name = it.full_name,
            name = it.name
        )
    }
    return domainList
}
