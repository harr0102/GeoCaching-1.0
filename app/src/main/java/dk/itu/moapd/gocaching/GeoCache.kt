package dk.itu.moapd.gocaching


class GeoCache(_cache: String, _where: String, _date: String?) {
    var cache = _cache
        get() = field
        set(value){
            field = value.trim()
        }
    var where = _where
    var date = _date

    override  fun  toString (): String {
        return "$cache  is  placed  at  $where at the following date $date"

    }

}