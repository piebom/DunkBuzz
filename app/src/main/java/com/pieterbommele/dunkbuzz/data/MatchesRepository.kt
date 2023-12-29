package com.pieterbommele.dunkbuzz.data

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
import com.pieterbommele.dunkbuzz.model.Match
import com.pieterbommele.dunkbuzz.network.Match.MatchApiService
import com.pieterbommele.dunkbuzz.network.Match.asDomainObjects
import com.pieterbommele.dunkbuzz.network.Match.getMatchesAsFlow
import com.pieterbommele.dunkbuzz.network.Team.asDomainObjects
import com.pieterbommele.dunkbuzz.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException
import java.util.UUID

/**
 * Interface defining the operations for managing matches data.
 */
interface MatchesRepository {
    /**
     * Retrieves a list of matches for a specific date.
     *
     * @param date The date for which matches are to be retrieved.
     * @return A Flow emitting a list of [Match] instances.
     */
    fun getMatches(date: String): Flow<List<Match>>

    /**
     * Retrieves a single match by its ID.
     *
     * @param id The ID of the match to be retrieved.
     * @return A Flow emitting the [Match] instance with the specified ID.
     */
    fun getMatch(id: Int): Flow<Match?>

    /**
     * Inserts a match into the repository.
     *
     * @param team The [Match] instance to be inserted.
     */
    suspend fun insertMatch(team: Match)

    /**
     * Deletes a match from the repository.
     *
     * @param team The [Match] instance to be deleted.
     */
    suspend fun deleteMatch(team: Match)

    /**
     * Updates an existing match in the repository.
     *
     * @param team The [Match] instance with updated information.
     */
    suspend fun updateMatch(team: Match)

    /**
     * Refreshes the match data for a specific date.
     *
     * @param date The date for which matches are to be refreshed.
     */
    suspend fun refresh(date: String)

    /**
     * Provides access to work information related to Wi-Fi connectivity checks.
     */
    var wifiWorkInfo: Flow<WorkInfo>
}

/**
 * A repository implementation that caches matches data and provides functionality to refresh the data from a network source.
 *
 * @property matchDao The data access object for match data.
 * @property matchApiService The API service for retrieving match data from the network.
 * @property context The context used to access system services and resources.
 */
class CachingMatchesRepository(private val matchDao: MatchDao, private val matchApiService: MatchApiService, context: Context) : MatchesRepository {

    override fun getMatches(date: String): Flow<List<Match>> {
        return matchDao.getAllItems(date).map {
            it.asDomainMatches()
        }.onEach {
            if (it.isEmpty()) {
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

    private var workID = UUID(1, 2)
    // the manager is private to the repository
    private val workManager = WorkManager.getInstance(context)
    // the info function is public
    override var wifiWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfoByIdFlow(workID)

    override suspend fun refresh(date: String) {
        // refresh is used to schedule the workrequest

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val requestBuilder = OneTimeWorkRequestBuilder<WifiNotificationWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
        workID = request.id
        wifiWorkInfo = workManager.getWorkInfoByIdFlow(request.id)

        // note the actual api request still uses coroutines
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
