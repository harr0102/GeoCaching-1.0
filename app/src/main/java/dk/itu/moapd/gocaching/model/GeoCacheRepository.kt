package dk.itu.moapd.gocaching.model

import android.app.Application
import androidx.lifecycle.LiveData
import dk.itu.moapd.gocaching.database.GeoCacheDB
import dk.itu.moapd.gocaching.database.GeoCacheDao

class GeoCacheRepository(application: Application) {

    private val geoCacheDao: GeoCacheDao
    private val geoCaches: LiveData<List<GeoCache>>

    init {
        val db = GeoCacheDB.get(application)
        geoCacheDao = db.geoCacheDao()
        geoCaches = geoCacheDao.getAll()
    }

    suspend fun add(geoCache: GeoCache) {
        geoCacheDao.add(geoCache)
    }

    suspend fun edit(geoCache: GeoCache) {
        geoCacheDao.edit(geoCache)
    }

    suspend fun remove(geoCache: GeoCache) {
        geoCacheDao.remove(geoCache)
    }

    fun getAll() = geoCaches

    fun getGeoCache(id: Int): LiveData<GeoCache?> = geoCacheDao.getGeoCache(id)
}