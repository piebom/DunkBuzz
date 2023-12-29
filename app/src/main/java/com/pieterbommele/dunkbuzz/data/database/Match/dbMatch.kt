package com.pieterbommele.dunkbuzz.data.database.Match

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

/**
 * Converts a [DbMatch] instance to a domain model [Match].
 *
 * This function maps the database match entity to a more general Match model used throughout the app.
 * It includes converting the embedded database team entities to domain team models.
 *
 * @return A [Match] instance containing the domain model representation of this database match.
 */
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

/**
 * Converts a domain model [Match] instance to a database entity [DbMatch].
 *
 * This function maps the domain match model to a database entity model. It includes converting the
 * domain team models to embedded database team entities.
 *
 * @return A [DbMatch] instance containing the database entity representation of the domain match.
 */
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

/**
 * Converts a list of [DbMatch] instances to a list of domain model [Match] instances.
 *
 * This function maps each database match entity in the list to its corresponding domain match model.
 * It is essentially a batch operation of [asDomainMatch] on a list.
 *
 * @return A list of [Match] instances representing the domain model matches.
 */
fun List<DbMatch>.asDomainMatches(): List<Match> {
    return this.map {
        it.asDomainMatch()
    }
}
