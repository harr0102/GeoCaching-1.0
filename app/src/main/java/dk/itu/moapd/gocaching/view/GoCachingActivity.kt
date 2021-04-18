package dk.itu.moapd.gocaching.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dk.itu.moapd.gocaching.controller.GoCachingFragment
import dk.itu.moapd.gocaching.R
import dk.itu.moapd.gocaching.app
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User

class GoCachingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_go_caching)

            val currentFragment = supportFragmentManager.findFragmentById((R.id.fragment_container))

            if (currentFragment == null) {
                val fragment = GoCachingFragment()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
            }

            Realm.init(this)
            var config = RealmConfiguration.Builder()
                .name("geo_cache_database.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build()

            Realm.setDefaultConfiguration(config)
            app = App(AppConfiguration.Builder("realmserver-kpkii").build())

            val anonymousCredentials: Credentials = Credentials.anonymous()
            var user: User?

            app.loginAsync(anonymousCredentials) {
                if (it.isSuccess) {
                    Log.v("AUTH", "Successfully authenticated anonymously.")
                    user = app.currentUser()

                } else {
                    Log.e("AUTH", it.error.toString())
                }
        }
    }
}