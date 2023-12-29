package com.pieterbommele.dunkbuzz.data.database.Team
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pieterbommele.dunkbuzz.model.Team

@Entity(tableName = "teams")
data class DbTeam(
    @PrimaryKey
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)

/**
 * Converts a [DbTeam] instance to a domain model [Team].
 *
 * This function maps the database team entity to a more general Team model used throughout the app.
 * It includes converting database fields to corresponding domain model fields.
 *
 * @return A [Team] instance containing the domain model representation of this database team.
 */
fun DbTeam.asDomainTeams(): Team {
    return Team(
        id = this.id,
        abbreviation = this.abbreviation,
        city = this.city,
        conference = this.conference,
        division = this.division,
        full_name = this.full_name,
        name = this.name
    )
}

/**
 * Converts a domain model [Team] instance to a database entity [DbTeam].
 *
 * This function maps the domain team model to a database entity model. It includes converting the
 * domain model fields to corresponding database entity fields.
 *
 * @return A [DbTeam] instance containing the database entity representation of the domain team.
 */
fun Team.asDbTeam(): DbTeam {
    return DbTeam(
        id = this.id,
        abbreviation = this.abbreviation,
        city = this.city,
        conference = this.conference,
        division = this.division,
        full_name = this.full_name,
        name = this.name
    )
}

/**
 * Converts a list of [DbTeam] instances to a list of domain model [Team] instances.
 *
 * This function maps each database team entity in the list to its corresponding domain team model.
 * It is essentially a batch operation of [asDomainTeams] on a list.
 *
 * @return A list of [Team] instances representing the domain model teams.
 */
fun List<DbTeam>.asDomainTeams(): List<Team> {
    return this.map {
        Team(it.id, it.abbreviation, it.city, it.conference, it.division, it.full_name, it.name)
    }
}
