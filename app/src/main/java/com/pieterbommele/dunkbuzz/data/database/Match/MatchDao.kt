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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbMatch)

    @Update
    suspend fun update(item: DbMatch)

    @Delete
    suspend fun delete(item: DbMatch)

    @Query("SELECT * from matches WHERE id = :id")
    fun getItem(id: Int): Flow<DbMatch>

    @Query("SELECT * from matches ORDER BY date desc")
    fun getAllItems(): Flow<List<DbMatch>>
}