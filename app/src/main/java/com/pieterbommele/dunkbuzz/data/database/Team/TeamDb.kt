package com.pieterbommele.dunkbuzz.data.database.Team
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [dbTeam::class], version = 3, exportSchema = false)
abstract class TeamDb : RoomDatabase() {

    abstract fun taskDao(): TeamDao

    companion object {
        @Volatile
        private var Instance: TeamDb? = null

        fun getDatabase(context: Context): TeamDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TeamDb::class.java, "dunkbuzz_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}