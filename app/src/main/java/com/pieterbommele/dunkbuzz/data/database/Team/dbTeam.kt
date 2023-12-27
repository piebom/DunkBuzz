package com.pieterbommele.dunkbuzz.data.database.Team
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pieterbommele.dunkbuzz.model.Team

@Entity(tableName = "teams")
data class dbTeam(
    @PrimaryKey
    val id : Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)

fun dbTeam.asDomainTeams(): Team {
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

fun Team.asDbTeam(): dbTeam {
    return dbTeam(
        id = this.id,
        abbreviation = this.abbreviation,
        city = this.city,
        conference = this.conference,
        division = this.division,
        full_name = this.full_name,
        name = this.name
    )
}

fun List<dbTeam>.asDomainTeams(): List<Team> {
    return this.map {
        Team(it.id,it.abbreviation, it.city, it.conference, it.division, it.full_name, it.name)
    }
}