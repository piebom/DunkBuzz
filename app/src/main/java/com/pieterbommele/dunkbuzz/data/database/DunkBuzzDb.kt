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
 * The Room database for the DunkBuzz application.
 *
 * This abstract class serves as the main access point to the persisted data. It includes definitions for the
 * DAOs that access the [DbTeam] and [DbMatch] entities.
 *
 * @property teamDao Provides access to the [TeamDao] for team-related database operations.
 * @property matchDao Provides access to the [MatchDao] for match-related database operations.
 */
@Database(entities = [DbTeam::class, DbMatch::class], version = 7, exportSchema = false)
abstract class DunkBuzzDb : RoomDatabase() {

    /**
     * Gets the [TeamDao] for accessing team data in the database.
     *
     * @return The [TeamDao] instance.
     */
    abstract fun teamDao(): TeamDao

    /**
     * Gets the [TeamDao] for accessing team data in the database.
     *
     * @return The [TeamDao] instance.
     */
    abstract fun matchDao(): MatchDao

    companion object {
        @Volatile
        private var Instance: DunkBuzzDb? = null

        /**
         * Gets the singleton instance of [DunkBuzzDb].
         *
         * This function creates a new instance of the database if it doesn't exist, otherwise, it returns
         * the existing instance. It ensures that only one instance of the database is created and used throughout
         * the application to prevent issues with concurrent access.
         *
         * @param context The context used to create the database instance.
         * @return The [DunkBuzzDb] singleton instance.
         */
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
