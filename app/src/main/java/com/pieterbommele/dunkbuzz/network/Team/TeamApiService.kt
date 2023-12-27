package com.pieterbommele.dunkbuzz.network.Team
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

// create the actual function implementations (expensive!)
// no longer needed --> moved to the AppContainer
// object TaskApi{
//
// }

// define what the API looks like
interface TeamApiService {
    // suspend is added to force the user to call this in a coroutine scope
    @GET("teams")
    suspend fun getTeams(): ApiResponseTeam
}

// helper function
fun TeamApiService.getTeamsAsFlow(): Flow<List<ApiTeam>> = flow {
    try {
        emit(getTeams().data)
    }
    catch(e: Exception){
        Log.e("API", "getTeamsAsFlow: "+e.stackTraceToString(), )
    }
}