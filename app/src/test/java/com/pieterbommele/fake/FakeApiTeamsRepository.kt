package com.pieterbommele.fake

import androidx.work.WorkInfo
import com.pieterbommele.dunkbuzz.data.TeamsRepository
import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.network.Team.asDomainObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mockito.Mockito.mock

class FakeApiTeamsRepository : TeamsRepository {

    private var _wifiWorkInfo: Flow<WorkInfo>? = null
    override fun getTeams(): Flow<List<Team>> {
        return flow {
            emit(FakeDataSource.teamsApi.asDomainObjects()) // Emit a copy of the current list of teams
        }
    }

    override fun getTeam(id: String): Flow<Team?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTeam(team: Team) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTeam(team: Team) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTeam(team: Team) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh() {
        return;
    }

    override var wifiWorkInfo: Flow<WorkInfo>
        get() = _wifiWorkInfo ?: flow {
            // Mock the WorkInfo object using Mockito or create a test object
            emit(mock(WorkInfo::class.java))
        }
        set(value) {
            _wifiWorkInfo = value
        }
}
