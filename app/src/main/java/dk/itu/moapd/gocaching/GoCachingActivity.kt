package dk.itu.moapd.gocaching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_go_caching.*

class GoCachingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_caching)

        val currentFragment = supportFragmentManager.findFragmentById((R.id.fragment_container))

        if(currentFragment == null){
            val fragment = GoCachingFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()
        }

    }
}