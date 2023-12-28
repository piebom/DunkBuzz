package com.pieterbommele.dunkbuzz.data

import com.pieterbommele.dunkbuzz.model.Team
import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.pieterbommele.dunkbuzz.data.database.Match.MatchDao
import com.pieterbommele.dunkbuzz.data.database.Match.asDbMatch
import com.pieterbommele.dunkbuzz.data.database.Match.asDomainMatch
import com.pieterbommele.dunkbuzz.data.database.Match.asDomainMatches
import com.pieterbommele.dunkbuzz.data.database.Team.asDbTeam
import com.pieterbommele.dunkbuzz.data.database.Team.asDomainTeams
import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.network.Match.MatchApiService
import com.pieterbommele.dunkbuzz.network.Match.asDomainObjects
import com.pieterbommele.dunkbuzz.network.Match.getMatchesAsFlow
import com.pieterbommele.dunkbuzz.network.Team.TeamApiService
import com.pieterbommele.dunkbuzz.network.Team.asDomainObjects
import com.pieterbommele.dunkbuzz.network.Team.getTeamsAsFlow
import com.pieterbommele.dunkbuzz.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException
import java.util.UUID

interface MatchesRepository {
    fun getMatches(date:String): Flow<List<Match>>

    fun getMatch(id: Int): Flow<Match?>

    suspend fun insertMatch(team: Match)

    suspend fun deleteMatch(team: Match)

    suspend fun updateMatch(team: Match)

    suspend fun refresh(date: String)

    var wifiWorkInfo: Flow<WorkInfo>
}

class CachingMatchesRepository(private val matchDao: MatchDao, private val matchApiService: MatchApiService, context: Context) : MatchesRepository {

    // this repo contains logic to refresh the tasks (remote)
    // sometimes that logic is written in a 'usecase'
    override fun getMatches(date:String): Flow<List<Match>> {
        return matchDao.getAllItems(date).map {
            it.asDomainMatches()
        }.onEach {
            if(it.isEmpty()){
                refresh(date)
            }
        }
    }

    override fun getMatch(id: Int): Flow<Match?> {
        return matchDao.getItem(id).map {
            it.asDomainMatch()
        }
    }

    override suspend fun insertMatch(task: Match) {
        matchDao.insert(task.asDbMatch())
    }

    override suspend fun deleteMatch(task: Match) {
        matchDao.delete(task.asDbMatch())
    }

    override suspend fun updateMatch(task: Match) {
        matchDao.update(task.asDbMatch())
    }

    private var workID = UUID(1,2)
    //the manager is private to the repository
    private val workManager = WorkManager.getInstance(context)
    //the info function is public
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workID)

    override suspend fun refresh(date: String) {
        //refresh is used to schedule the workrequest

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val requestBuilder = OneTimeWorkRequestBuilder<WifiNotificationWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
        workID = request.id
        wifiWorkInfo = workManager.getWorkInfoByIdFlow(request.id)



        //note the actual api request still uses coroutines
        try {
            matchApiService.getMatchesAsFlow(date = date).asDomainObjects().collect {
                    value ->
                for (task in value) {
                    Log.i("TEST", "refresh: $value")
                    insertMatch(task)
                }
            }
        } catch (e: SocketTimeoutException) {
            // log something
        }
    }
}