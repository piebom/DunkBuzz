package com.pieterbommele.dunkbuzz.data.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pieterbommele.dunkbuzz.data.database.Match.DbMatch
import com.pieterbommele.dunkbuzz.data.database.Match.MatchDao
import com.pieterbommele.dunkbuzz.data.database.Team.DbTeam
import com.pieterbommele.dunkbuzz.data.database.Team.TeamDao

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [DbTeam::class,DbMatch::class], version = 7, exportSchema = false)
abstract class DunkBuzzDb : RoomDatabase() {

    abstract fun teamDao(): TeamDao
    abstract fun matchDao(): MatchDao

    companion object {
        @Volatile
        private var Instance: DunkBuzzDb? = null

        fun getDatabase(context: Context): DunkBuzzDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DunkBuzzDb::class.java, "dunkbuzz_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}