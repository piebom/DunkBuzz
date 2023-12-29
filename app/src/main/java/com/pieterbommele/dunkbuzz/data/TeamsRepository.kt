package com.pieterbommele.dunkbuzz.data

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
import com.pieterbommele.dunkbuzz.model.Team
import com.pieterbommele.dunkbuzz.network.Team.TeamApiService
import com.pieterbommele.dunkbuzz.network.Team.asDomainObjects
import com.pieterbommele.dunkbuzz.network.Team.getTeamsAsFlow
import com.pieterbommele.dunkbuzz.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException
import java.util.UUID

/**
 * Interface defining the operations for managing teams data.
 */
interface TeamsRepository {
    /**
     * Retrieves a list of all teams.
     *
     * @return A Flow emitting a list of [Team] instances.
     */
    fun getTeams(): Flow<List<Team>>

    /**
     * Retrieves a single team by its ID.
     *
     * @param id The ID of the team to be retrieved.
     * @return A Flow emitting the [Team] instance with the specified ID.
     */
    fun getTeam(id: String): Flow<Team?>

    /**
     * Inserts a team into the repository.
     *
     * @param team The [Team] instance to be inserted.
     */
    suspend fun insertTeam(team: Team)

    /**
     * Deletes a team from the repository.
     *
     * @param team The [Team] instance to be deleted.
     */
    suspend fun deleteTeam(team: Team)

    /**
     * Updates an existing team in the repository.
     *
     * @param team The [Team] instance with updated information.
     */
    suspend fun updateTeam(team: Team)

    /**
     * Refreshes the teams data.
     */
    suspend fun refresh()

    /**
     * Provides access to work information related to Wi-Fi connectivity checks.
     */
    var wifiWorkInfo: Flow<WorkInfo>
}

/**
 * A repository implementation that caches teams data and provides functionality to refresh the data from a network source.
 *
 * @property teamDao The data access object for team data.
 * @property teamApiService The API service for retrieving team data from the network.
 * @property context The context used to access system services and resources.
 */
class CachingTeamsRepository(private val teamDao: TeamDao, private val teamApiService: TeamApiService, context: Context) : TeamsRepository {

    override fun getTeams(): Flow<List<Team>> {
        return teamDao.getAllItems().map {
            it.asDomainTeams()
        }.onEach {
            if (it.isEmpty()) {
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

    private var workID = UUID(1, 2)
    // the manager is private to the repository
    private val workManager = WorkManager.getInstance(context)
    // the info function is public
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workID)

    override suspend fun refresh() {
        // refresh is used to schedule the workrequest

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val requestBuilder = OneTimeWorkRequestBuilder<WifiNotificationWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
        workID = request.id
        wifiWorkInfo = workManager.getWorkInfoByIdFlow(request.id)

        // note the actual api request still uses coroutines
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
