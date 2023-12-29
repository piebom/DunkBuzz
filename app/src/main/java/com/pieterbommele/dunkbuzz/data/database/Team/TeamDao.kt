package com.pieterbommele.dunkbuzz.data.database.Team
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    /**
     * Inserts a [DbTeam] into the database.
     *
     * If a team with the same ID already exists, it will be replaced to avoid conflicts.
     *
     * @param item The [DbTeam] entity to be inserted into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbTeam)

    /**
     * Updates an existing [DbTeam] in the database.
     *
     * The team is identified by its primary key (ID), and all its fields will be updated to the new values.
     *
     * @param item The [DbTeam] entity with updated information.
     */
    @Update
    suspend fun update(item: DbTeam)

    /**
     * Deletes a [DbTeam] from the database.
     *
     * @param item The [DbTeam] entity to be removed from the database.
     */
    @Delete
    suspend fun delete(item: DbTeam)

    /**
     * Retrieves a single [DbTeam] by its name.
     *
     * This query will return a Flow that emits the latest data from the database upon subscription
     * and subsequently whenever the data changes.
     *
     * @param name The name of the team to be retrieved.
     * @return A Flow emitting the [DbTeam] entity with the specified name.
     */
    @Query("SELECT * from teams WHERE name = :name")
    fun getItem(name: String): Flow<DbTeam>

    /**
     * Retrieves all [DbTeam] entities from the database, ordered by name in ascending order.
     *
     * This query returns a Flow that emits the latest list of teams from the database upon subscription
     * and subsequently whenever the data changes.
     *
     * @return A Flow emitting a list of all [DbTeam] entities in the database, ordered by name.
     */
    @Query("SELECT * from teams ORDER BY name ASC")
    fun getAllItems(): Flow<List<DbTeam>>
}
