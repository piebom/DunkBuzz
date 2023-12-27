package com.pieterbommele.dunkbuzz.network.Match

import android.util.Log
import com.pieterbommele.dunkbuzz.network.Team.ApiTeam
import com.pieterbommele.dunkbuzz.network.Team.TeamApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

interface MatchApiService {
    // suspend is added to force the user to call this in a coroutine scope
    @GET("games?dates[]=27/12/2023")
    suspend fun getMatches(): ApiResponseMatch
}

// helper function
fun MatchApiService.getMatchesAsFlow(): Flow<List<ApiMatch>> = flow {
    try {
        emit(getMatches().data)
    }
    catch(e: Exception){
        Log.e("API", "getMatchesAsFlow: "+e.stackTraceToString(), )
    }
}