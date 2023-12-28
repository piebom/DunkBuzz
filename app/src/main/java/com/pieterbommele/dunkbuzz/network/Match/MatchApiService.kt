package com.pieterbommele.dunkbuzz.network.Match

import android.util.Log
import com.pieterbommele.dunkbuzz.network.Team.ApiTeam
import com.pieterbommele.dunkbuzz.network.Team.TeamApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchApiService {
    // suspend is added to force the user to call this in a coroutine scope
    @GET("games")
    suspend fun getMatches(@Query("dates[]") date: String): ApiResponseMatch
}

// helper function
fun MatchApiService.getMatchesAsFlow(date:String): Flow<List<ApiMatch>> = flow {
    try {
        emit(getMatches(date = date).data)
    }
    catch(e: Exception){
        Log.e("API", "getMatchesAsFlow: "+e.stackTraceToString(), )
        emit(emptyList())
    }
}