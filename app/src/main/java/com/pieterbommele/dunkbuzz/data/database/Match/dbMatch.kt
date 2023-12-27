package com.pieterbommele.dunkbuzz.data.database.Match

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pieterbommele.dunkbuzz.data.database.Team.DbTeam
import com.pieterbommele.dunkbuzz.data.database.Team.asDbTeam
import com.pieterbommele.dunkbuzz.data.database.Team.asDomainTeams
import com.pieterbommele.dunkbuzz.model.Match

@Entity(tableName = "matches")
data class DbMatch(
    @PrimaryKey
    val id: Int,
    val date: String,
    @Embedded(prefix = "home_team_")
    val homeTeam: DbTeam,
    val homeTeamScore: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    @Embedded(prefix = "visitor_team_")
    val visitorTeam: DbTeam,
    val visitorTeamScore: Int
)

fun DbMatch.asDomainMatch(): Match {
    return Match(
        id = this.id,
        date = this.date,
        homeTeam = this.homeTeam.asDomainTeams(),
        homeTeamScore = this.homeTeamScore,
        period = this.period,
        postseason = this.postseason,
        season = this.season,
        status = this.status,
        time = this.time,
        visitorTeam = this.visitorTeam.asDomainTeams(),
        visitorTeamScore = this.visitorTeamScore
    )
}
fun Match.asDbMatch(): DbMatch {
    return DbMatch(
        id = this.id,
        date = this.date,
        homeTeam = this.homeTeam.asDbTeam(),
        homeTeamScore = this.homeTeamScore,
        period = this.period,
        postseason = this.postseason,
        season = this.season,
        status = this.status,
        time = this.time,
        visitorTeam = this.visitorTeam.asDbTeam(),
        visitorTeamScore = this.visitorTeamScore
    )
}

fun List<DbMatch>.asDomainMatches(): List<Match> {
    return this.map {
        it.asDomainMatch()
    }
}
