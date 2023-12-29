package com.pieterbommele.dunkbuzz.model

/**
 * Represents a basketball match with details about the teams involved and the match's specifics.
 *
 * @property id The unique identifier for the match.
 * @property date The date when the match is scheduled to occur.
 * @property homeTeam An instance of [Team] representing the home team.
 * @property homeTeamScore The score achieved by the home team. This may be final or current, depending on the match status.
 * @property period The period or stage of the match (e.g., quarter, half, etc.).
 * @property postseason Indicates whether the match is part of the postseason (playoffs).
 * @property season The season year the match is part of.
 * @property status The current status of the match (e.g., scheduled, in progress, completed).
 * @property time The scheduled start time for the match.
 * @property visitorTeam An instance of [Team] representing the visiting team.
 * @property visitorTeamScore The score achieved by the visiting team. This may be final or current, depending on the match status.
 */
data class Match(
    val id: Int,
    val date: String,
    val homeTeam: Team,
    val homeTeamScore: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    val visitorTeam: Team,
    val visitorTeamScore: Int
)
