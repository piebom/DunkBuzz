package com.pieterbommele.dunkbuzz.network.Team
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

/**
 * Defines the network operations related to basketball teams.
 *
 * This interface declares the API endpoints for fetching team data using Retrofit. The operations
 * are defined as suspend functions to ensure they're called within a coroutine, providing
 * built-in support for asynchronous operations.
 */
interface TeamApiService {
    /**
     * Fetches a list of all teams from the API.
     *
     * This suspend function should be called within a coroutine context. It fetches all basketball teams' data
     * provided by the API and encapsulates them in an [ApiResponseTeam] object.
     *
     * @return [ApiResponseTeam] containing the list of teams and additional metadata.
     * @throws Exception for any network or conversion errors.
     */
    @GET("teams")
    suspend fun getTeams(): ApiResponseTeam
}

/**
 * Converts the network response for teams into a Flow.
 *
 * This function calls the [getTeams] method of [TeamApiService] and emits the resulting data as a flow.
 * If an exception occurs, it logs the error and continues emitting the flow without data.
 *
 * @return A [Flow] of [List] of [ApiTeam] that emits the list of teams.
 */
fun TeamApiService.getTeamsAsFlow(): Flow<List<ApiTeam>> = flow {
    try {
        emit(getTeams().data)
    } catch (e: Exception) {
        Log.e("API", "getTeamsAsFlow: " + e.stackTraceToString(),)
    }
}
