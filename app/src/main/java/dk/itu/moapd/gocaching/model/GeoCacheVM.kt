package dk.itu.moapd.gocaching.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GeoCacheVM(application: Application): AndroidViewModel(application) {

    private val geoCacheRepository: GeoCacheRepository = GeoCacheRepository(application)
    private val geoCaches: LiveData<List<GeoCache>> = geoCacheRepository.getAll()

    fun add(geoCache: GeoCache) = viewModelScope.launch {
        geoCacheRepository.add(geoCache)
    }

    fun edit(geoCache: GeoCache) = viewModelScope.launch {
        geoCacheRepository.edit(geoCache)
    }

    fun remove(geoCache: GeoCache) = viewModelScope.launch {
        geoCacheRepository.remove(geoCache)
    }

    fun getAll(): LiveData<List<GeoCache>> = geoCaches

    fun getGeoCache(id: Int) = geoCacheRepository.getGeoCache(id)

}