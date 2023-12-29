package com.pieterbommele.dunkbuzz.network.Match

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service interface for match-related network operations.
 *
 * This interface defines the API calls related to basketball matches and the necessary annotations for Retrofit to
 * convert these into HTTP requests.
 */
interface MatchApiService {
    /**
     * Retrieves match data for a specific date from the API.
     *
     * This is a suspend function and should be called from a coroutine context. It fetches the list of matches
     * scheduled or played on the provided date.
     *
     * @param date The date for which matches data is to be retrieved.
     * @return [ApiResponseMatch] containing the list of matches and meta information.
     * @throws Exception for any network or conversion errors.
     */
    @GET("games")
    suspend fun getMatches(@Query("dates[]") date: String): ApiResponseMatch
}

/**
 * A helper function to convert the network response for matches into a Flow.
 *
 * This function calls the [getMatches] method of [MatchApiService] and emits the resulting data as a flow.
 * If an exception occurs, it logs the error and emits an empty list.
 *
 * @param date The date for which matches data is to be retrieved.
 * @return A [Flow] of [List] of [ApiMatch] that emits the list of matches for the specified date.
 */
fun MatchApiService.getMatchesAsFlow(date: String): Flow<List<ApiMatch>> = flow {
    try {
        emit(getMatches(date = date).data)
    } catch (e: Exception) {
        Log.e("API", "getMatchesAsFlow: " + e.stackTraceToString(),)
        emit(emptyList())
    }
}
