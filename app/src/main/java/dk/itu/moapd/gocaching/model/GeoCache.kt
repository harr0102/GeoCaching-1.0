package dk.itu.moapd.gocaching.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geoCaches_table")
data class GeoCache(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "cache")
    var cache: String = "",

    @ColumnInfo(name = "where")
    var where: String = "",

    @ColumnInfo(name = "dateCreated")
    var dateCreated: String = "",

    @ColumnInfo(name = "dateEdited")
    var dateEdited: String = ""

)


/*class GeoCache(_cache: String, _where: String, _date: String?, _updatedDate: String?) {
    var cache = _cache
        get() = field
        set(value){
            field = value.trim()
        }
    var where = _where
    var date = _date
    var updatedDate = _updatedDate

    override  fun  toString (): String {
        return "$cache  is  placed  at  $where at the following date $date. Updated at $updatedDate."
    }
}*/
