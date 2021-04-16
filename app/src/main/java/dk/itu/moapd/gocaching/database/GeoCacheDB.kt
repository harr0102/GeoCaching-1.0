package dk.itu.moapd.gocaching.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dk.itu.moapd.gocaching.model.GeoCache

@Database(entities = [GeoCache::class], version = 1, exportSchema = false)
abstract class GeoCacheDB : RoomDatabase() {
    abstract fun geoCacheDao(): GeoCacheDao
    companion object {
        // Use a singleton to prevent multiple instances of database
        // opening at the same time.
        @Volatile
        private var instance: GeoCacheDB? = null

        fun get(context: Context): GeoCacheDB {
            val checkInstance = instance
            if (checkInstance != null)
                return checkInstance
            synchronized(this) {
                val created = Room.databaseBuilder(
                    context.applicationContext,
                    GeoCacheDB::class.java, "geoCache_database")
                    .build()
                instance = created
                return created
            }
        }
    }
}