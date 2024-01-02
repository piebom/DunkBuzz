package com.pieterbommele.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.pieterbommele.FakeDataSource
import com.pieterbommele.dunkbuzz.data.database.DunkBuzzDb
import com.pieterbommele.dunkbuzz.data.database.Match.MatchDao
import com.pieterbommele.dunkbuzz.data.database.Match.asDbMatch
import com.pieterbommele.dunkbuzz.data.database.Match.asDomainMatch
import com.pieterbommele.dunkbuzz.data.database.Match.asDomainMatches
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MatchDaoTest {
    private lateinit var matchDao: MatchDao
    private lateinit var db: DunkBuzzDb
    private var date = "2023-12-23T00:00:00.000Z"

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, DunkBuzzDb::class.java)
            .allowMainThreadQueries()
            .build()
        matchDao = db.matchDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun DaoInsertMatchTest() = runBlocking {
        matchDao.insert(FakeDataSource.matches[0].asDbMatch())
        val match = matchDao.getAllItems(date).map {
            it.asDomainMatches()
        }.first()[0]
        assertEquals(FakeDataSource.matches[0], match)
    }

    @Test
    @Throws(Exception::class)
    fun DaoInsertMatchesTest() = runBlocking {
        FakeDataSource.matches.forEach {
            matchDao.insert(it.asDbMatch())
        }
        val matches = matchDao.getAllItems(date).map {
            it.asDomainMatches()
        }.first()
        assertTrue(matches.containsAll(FakeDataSource.matches.filter { it.date == date }.map { it }))
    }

    @Test
    @Throws(Exception::class)
    fun DaoDeleteMatchTest() = runBlocking {
        matchDao.insert(FakeDataSource.matches[0].asDbMatch())
        matchDao.delete(FakeDataSource.matches[0].asDbMatch())
        val matches = matchDao.getAllItems(date).map {
            it.asDomainMatches()
        }.first()
        assertTrue(matches.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun DaoGetByIdTest() = runBlocking {
        FakeDataSource.matches.forEach {
            matchDao.insert(it.asDbMatch())
        }
        val match = matchDao.getItem(1).map {
            it.asDomainMatch()
        }.first()
        assertEquals(FakeDataSource.matches[0], match)
    }
}
