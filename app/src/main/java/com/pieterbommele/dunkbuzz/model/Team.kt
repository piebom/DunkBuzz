package com.pieterbommele.dunkbuzz.model

/**
 * Represents a team with detailed information in the context of basketball matches.
 *
 * @property id The unique identifier for the team.
 * @property abbreviation The standard abbreviation used to refer to the team (e.g., "LAL" for Los Angeles Lakers).
 * @property city The city or location the team is associated with.
 * @property conference The conference the team belongs to (e.g., "East" or "West" in the NBA).
 * @property division The division within the conference that the team is part of.
 * @property full_name The full, official name of the team (e.g., "Los Angeles Lakers").
 * @property name The common name of the team (e.g., "Lakers").
 */
data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)
