package dk.itu.moapd.gocaching.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required


open class GeoCache (
    @PrimaryKey var id: Int = 0,
    @Required var cache: String = "",
    @Required var where: String = "",
    @Required var dateOfCreation: String = "",
    @Required var dateUpdated: String = ""
        ) : RealmObject()


