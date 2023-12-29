package com.pieterbommele.dunkbuzz.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pieterbommele.dunkbuzz.data.database.DunkBuzzDb
import com.pieterbommele.dunkbuzz.network.Match.MatchApiService
import com.pieterbommele.dunkbuzz.network.NetworkConnectionInterceptor
import com.pieterbommele.dunkbuzz.network.Team.TeamApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Defines the container for application-level dependencies.
 *
 * This interface provides access to repository instances which are used for data operations throughout the application.
 */
interface AppContainer {
    /**
     * Provides access to the [TeamsRepository] instance for team-related data operations.
     */
    val teamsRepository: TeamsRepository

    /**
     * Provides access to the [MatchesRepository] instance for match-related data operations.
     */
    val matchesRepository: MatchesRepository
}

/**
 * The default implementation of [AppContainer] that sets up and provides the necessary dependencies for the application.
 *
 * @property context The context used to create database and network instances.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {

    private val networkCheck = NetworkConnectionInterceptor(context)
    private val client = OkHttpClient.Builder()
        .addInterceptor(networkCheck)
        .build()

    private val baseUrl = "https://www.balldontlie.io/api/v1/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json { ignoreUnknownKeys = true; coerceInputValues = true }.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .client(client)
        .build()

    /**
     * Provides a Retrofit service instance for team-related network operations.
     */
    private val retrofitServiceTeam: TeamApiService by lazy {
        retrofit.create(TeamApiService::class.java)
    }

    /**
     * Provides a Retrofit service instance for match-related network operations.
     */
    private val retrofitServiceMatch: MatchApiService by lazy {
        retrofit.create(MatchApiService::class.java)
    }

    /**
     * Provides a repository for managing teams data, both from the local database and network.
     */
    override val teamsRepository: TeamsRepository by lazy {
        CachingTeamsRepository(DunkBuzzDb.getDatabase(context = context).teamDao(), retrofitServiceTeam, context)
    }

    /**
     * Provides a repository for managing matches data, both from the local database and network.
     */
    override val matchesRepository: MatchesRepository by lazy {
        CachingMatchesRepository(
            DunkBuzzDb.getDatabase(context = context).matchDao(),
            retrofitServiceMatch,
            context
        )
    }
}
