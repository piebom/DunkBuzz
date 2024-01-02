package com.pieterbommele.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pieterbommele.FakeDataSource
import com.pieterbommele.dunkbuzz.data.database.DunkBuzzDb
import com.pieterbommele.dunkbuzz.data.database.Team.TeamDao
import com.pieterbommele.dunkbuzz.data.database.Team.asDbTeam
import com.pieterbommele.dunkbuzz.data.database.Team.asDomainTeams
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TeamDaoTest {
    private lateinit var teamDao: TeamDao
    private lateinit var db: DunkBuzzDb

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, DunkBuzzDb::class.java)
            .allowMainThreadQueries()
            .build()
        teamDao = db.teamDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun DaoInsertTeamTest() = runBlocking {
        teamDao.insert(FakeDataSource.teams[0].asDbTeam())
        val team = teamDao.getAllItems().first()[0].asDomainTeams()
        assertEquals(FakeDataSource.teams[0], team)
    }

    @Test
    @Throws(Exception::class)
    fun DaoInsertTeamsTest() = runBlocking {
        FakeDataSource.teams.forEach {
            teamDao.insert(it.asDbTeam())
        }
        val teams = teamDao.getAllItems().map {
            it.asDomainTeams()
        }.first()
        assertEquals(FakeDataSource.teams, teams)
    }

    @Test
    @Throws(Exception::class)
    fun DaoDeleteTeamTest() = runBlocking {
        teamDao.insert(FakeDataSource.teams[0].asDbTeam())
        teamDao.delete(FakeDataSource.teams[0].asDbTeam())
        val teams = teamDao.getAllItems().first().asDomainTeams()
        assertTrue(teams.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun DaoGetTeamTest() = runBlocking {
        teamDao.insert(FakeDataSource.teams[0].asDbTeam())
        val team = teamDao.getItem("Hawks").first().asDomainTeams()
        assertEquals(FakeDataSource.teams[0], team)
    }
}
