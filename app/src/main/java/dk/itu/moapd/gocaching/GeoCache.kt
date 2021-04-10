package dk.itu.moapd.gocaching


class GeoCache(_cache: String, _where: String, _date: String?, _updatedDate: String?) {
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


}