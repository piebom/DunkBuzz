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

interface AppContainer {
    val teamsRepository: TeamsRepository
    val matchesRepository: MatchesRepository
}

// container that takes care of dependencies
class DefaultAppContainer(private val context: Context) : AppContainer {

    private val networkCheck = NetworkConnectionInterceptor(context)
    private val client = OkHttpClient.Builder()
        .addInterceptor(networkCheck)
        .build()


    private val baseUrl = "https://www.balldontlie.io/api/v1/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json { ignoreUnknownKeys = true;coerceInputValues = true }.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val retrofitServiceTeam: TeamApiService by lazy {
        retrofit.create(TeamApiService::class.java)
    }

    private val retrofitServiceMatch: MatchApiService by lazy {
        retrofit.create(MatchApiService::class.java)
    }

    /*
    override val tasksRepository: TasksRepository by lazy {
        ApiTasksRepository(retrofitService)
    }
    */
    override val teamsRepository: TeamsRepository by lazy {
        CachingTeamsRepository(DunkBuzzDb.getDatabase(context = context).teamDao(), retrofitServiceTeam, context)

    }
    override val matchesRepository: MatchesRepository by lazy {
        CachingMatchesRepository(
            DunkBuzzDb.getDatabase(context = context).matchDao(),
            retrofitServiceMatch,
            context
        )
    }
    }