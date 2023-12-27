package com.pieterbommele.dunkbuzz.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pieterbommele.dunkbuzz.data.database.Team.TeamDb
import com.pieterbommele.dunkbuzz.network.NetworkConnectionInterceptor
import com.pieterbommele.dunkbuzz.network.TeamApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val teamsRepository: TeamsRepository
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
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val retrofitService: TeamApiService by lazy {
        retrofit.create(TeamApiService::class.java)
    }

    /*
    override val tasksRepository: TasksRepository by lazy {
        ApiTasksRepository(retrofitService)
    }
    */
    override val teamsRepository: TeamsRepository by lazy {
        CachingTasksRepository(TeamDb.getDatabase(context = context).taskDao(), retrofitService, context)
    }
}