package com.pieterbommele.fake

import com.pieterbommele.dunkbuzz.network.ApiMeta
import com.pieterbommele.dunkbuzz.network.Team.ApiResponseTeam
import com.pieterbommele.dunkbuzz.network.Team.ApiTeam
import com.pieterbommele.dunkbuzz.network.Team.TeamApiService

class FakeApiTeamsService : TeamApiService {
    override suspend fun getTeams(): ApiResponseTeam {
        return ApiResponseTeam(
            data = FakeDataSource.teamsApi,
            meta = ApiMeta(1, 1, 1, 1,3)
        )
    }
}