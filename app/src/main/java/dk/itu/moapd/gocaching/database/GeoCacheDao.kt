package dk.itu.moapd.gocaching.database

import androidx.lifecycle.LiveData
import androidx.room.*
import dk.itu.moapd.gocaching.model.GeoCache

@Dao
interface GeoCacheDao {

    @Insert
    suspend fun add(geoCache: GeoCache?)

    @Update
    suspend fun edit(geoCache: GeoCache?)

    @Delete
    suspend fun remove(geoCache: GeoCache?)

    @Query("DELETE FROM geoCaches_table")
    suspend fun removeAll()

    @Query("SELECT * FROM geoCaches_table")
    fun getAll(): LiveData<List<GeoCache>>

    @Query("SELECT * FROM geoCaches_table WHERE id = (:id)")
    fun getGeoCache(id: Int): LiveData<GeoCache?>
}