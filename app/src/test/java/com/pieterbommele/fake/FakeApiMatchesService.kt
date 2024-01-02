package com.pieterbommele.fake

import com.pieterbommele.dunkbuzz.network.ApiMeta
import com.pieterbommele.dunkbuzz.network.Match.ApiResponseMatch
import com.pieterbommele.dunkbuzz.network.Match.MatchApiService
import com.pieterbommele.dunkbuzz.network.Team.ApiResponseTeam

class FakeApiMatchesService : MatchApiService {
    override suspend fun getMatches(date: String): ApiResponseMatch {
        return ApiResponseMatch(
            data = FakeDataSource.matchesApi,
            meta = ApiMeta(1, 1, 1, 1,3)
        )
    }
}