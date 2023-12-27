package com.pieterbommele.dunkbuzz.model

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