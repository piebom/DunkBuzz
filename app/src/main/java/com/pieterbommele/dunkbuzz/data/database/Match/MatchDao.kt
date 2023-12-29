package com.pieterbommele.dunkbuzz.data.database.Match

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    /**
     * Inserts a [DbMatch] into the database.
     *
     * If a match with the same ID already exists, it will be replaced.
     *
     * @param item The [DbMatch] entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbMatch)

    /**
     * Updates an existing [DbMatch] in the database.
     *
     * The match is identified by its primary key (ID), and all its fields will be updated.
     *
     * @param item The [DbMatch] entity with updated fields.
     */
    @Update
    suspend fun update(item: DbMatch)

    /**
     * Deletes a [DbMatch] from the database.
     *
     * @param item The [DbMatch] entity to be deleted.
     */
    @Delete
    suspend fun delete(item: DbMatch)

    /**
     * Retrieves a single [DbMatch] by its ID.
     *
     * This query will return a Flow that emits the latest data from the database upon subscription
     * and subsequently whenever the data changes.
     *
     * @param id The ID of the match to be retrieved.
     * @return A Flow emitting the [DbMatch] entity with the specified ID.
     */
    @Query("SELECT * from matches WHERE id = :id")
    fun getItem(id: Int): Flow<DbMatch>

    /**
     * Retrieves all [DbMatch] entities for a given date.
     *
     * This query returns a Flow that emits the latest list of matches from the database upon subscription
     * and subsequently whenever the data changes.
     *
     * @param date The date for which matches should be retrieved.
     * @return A Flow emitting a list of [DbMatch] entities matching the specified date.
     */
    @Query("SELECT * from matches WHERE date = :date")
    fun getAllItems(date: String): Flow<List<DbMatch>>
}
