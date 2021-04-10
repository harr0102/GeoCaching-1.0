package dk.itu.moapd.gocaching

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GeoCacheDB private constructor(context: Context) {

    // https://mkyong.com/java/java-date-and-calendar-examples/ example 1.3 - I have used the same Date format.
    var dateFormat: SimpleDateFormat? = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    var date = Date()


    private val geoCaches = ArrayList<GeoCache>()
    private var lastCache = GeoCache("", "", null, null)


    init {
        geoCaches.add(
                GeoCache("Chair", "ITU", "2021/02/05 14:53:12", null)
        )
        geoCaches.add(
                GeoCache("Bike", "Fields", "2020/12/16 09:15:42", null)
        )
        geoCaches.add(
                GeoCache("Ticket", "Kobenhavns Lufthavn", "2020/11/29 23:00:50", null)
        )
    }

    companion object : GeoCacheDBHolder<GeoCacheDB, Context>(::GeoCacheDB)

    fun getGeoCaches(): List<GeoCache> {
        return geoCaches
    }
    fun addGeoCache(cache: String, where: String) {
        lastCache = GeoCache(cache, where, dateFormat?.format(date), null)
        geoCaches.add(lastCache)
    }
    fun updateGeoCache(cache: String, where: String, updatedDate: Date) {
        lastCache.cache = cache
        lastCache.where = where
        //lastCache.date = dateFormat?.format(date)
        lastCache.updatedDate = dateFormat?.format(updatedDate)

    }
    fun getLastGeoCacheInfo(): String {
        return lastCache.toString()
    }

    fun remove(i: Int) {
        geoCaches.removeAt(i)
    }
    /*private fun randomDate(): Long {
// This function gets the current timestamp and
// generates a random date in the last 365 days .

        val random = Random()
        val now = System.currentTimeMillis()
        val year = random.nextDouble() * 1000 * 60 * 60 * 24 * 365
        return(now - year).toLong()
    }*/
}


open class GeoCacheDBHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun get(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null)
            return checkInstance

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null)
                checkInstanceAgain
            else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}