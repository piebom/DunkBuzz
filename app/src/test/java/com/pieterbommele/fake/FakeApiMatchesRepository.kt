package com.pieterbommele.fake

import androidx.work.WorkInfo
import com.pieterbommele.dunkbuzz.data.MatchesRepository
import com.pieterbommele.dunkbuzz.model.Match
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mockito.Mockito

class FakeApiMatchesRepository : MatchesRepository {
    private var _wifiWorkInfo: Flow<WorkInfo>? = null
    override fun getMatches(date: String): Flow<List<Match>> {
        return flow {
            emit(FakeDataSource.matches) // Emit a copy of the current list of teams
        }
    }

    override fun getMatch(id: Int): Flow<Match?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMatch(team: Match) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMatch(team: Match) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMatch(team: Match) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(date: String) {
        return
    }

    override var wifiWorkInfo: Flow<WorkInfo>
        get() = _wifiWorkInfo ?: flow {
            // Mock the WorkInfo object using Mockito or create a test object
            emit(Mockito.mock(WorkInfo::class.java))
        }
        set(value) {
            _wifiWorkInfo = value
        }
}
