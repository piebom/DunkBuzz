package com.pieterbommele.dunkbuzz.network

import com.pieterbommele.dunkbuzz.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val data: List<ApiTeam>,
    val meta: ApiMeta
)

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

// extension function for an ApiTask List to convert is to a Domain Task List
fun Flow<List<ApiTeam>>.asDomainObjects(): Flow<List<Team>> {
    return map {
        it.asDomainObjects()
    }
}

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