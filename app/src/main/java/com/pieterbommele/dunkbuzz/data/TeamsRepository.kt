package com.pieterbommele.dunkbuzz.data

import com.pieterbommele.dunkbuzz.model.Team
import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.pieterbommele.dunkbuzz.data.database.Team.TeamDao
import com.pieterbommele.dunkbuzz.data.database.Team.asDbTeam
import com.pieterbommele.dunkbuzz.data.database.Team.asDomainTeams
import com.pieterbommele.dunkbuzz.network.TeamApiService
import com.pieterbommele.dunkbuzz.network.asDomainObjects
import com.pieterbommele.dunkbuzz.network.getTeamsAsFlow
import com.pieterbommele.dunkbuzz.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException
import java.util.UUID

interface TeamsRepository {
    // all items from datasource
    fun getTeams(): Flow<List<Team>>

    // one specific item
    fun getTeam(id: String): Flow<Team?>

    suspend fun insertTeam(team: Team)

    suspend fun deleteTeam(team: Team)

    suspend fun updateTeam(team: Team)

    suspend fun refresh()

    var wifiWorkInfo: Flow<WorkInfo>
}

class CachingTasksRepository(private val teamDao: TeamDao, private val teamApiService: TeamApiService, context: Context) : TeamsRepository {

    // this repo contains logic to refresh the tasks (remote)
    // sometimes that logic is written in a 'usecase'
    override fun getTeams(): Flow<List<Team>> {
        return teamDao.getAllItems().map {
            it.asDomainTeams()
        }.onEach {
            if(it.isEmpty()){
                refresh()
            }
        }
    }

    override fun getTeam(name: String): Flow<Team?> {
        return teamDao.getItem(name).map {
            it.asDomainTeams()
        }
    }

    override suspend fun insertTeam(task: Team) {
        teamDao.insert(task.asDbTeam())
    }

    override suspend fun deleteTeam(task: Team) {
        teamDao.delete(task.asDbTeam())
    }

    override suspend fun updateTeam(task: Team) {
        teamDao.update(task.asDbTeam())
    }

    private var workID = UUID(1,2)
    //the manager is private to the repository
    private val workManager = WorkManager.getInstance(context)
    //the info function is public
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workID)

    override suspend fun refresh() {
        //refresh is used to schedule the workrequest

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val requestBuilder = OneTimeWorkRequestBuilder<WifiNotificationWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
        workID = request.id
        wifiWorkInfo = workManager.getWorkInfoByIdFlow(request.id)



        //note the actual api request still uses coroutines
        try {
            teamApiService.getTeamsAsFlow().asDomainObjects().collect {
                    value ->
                for (task in value) {
                    Log.i("TEST", "refresh: $value")
                    insertTeam(task)
                }
            }
        } catch (e: SocketTimeoutException) {
            // log something
        }
    }
}
