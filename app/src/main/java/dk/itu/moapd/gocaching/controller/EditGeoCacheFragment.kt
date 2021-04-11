package dk.itu.moapd.gocaching.controller
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dk.itu.moapd.gocaching.GeoCacheDB
import dk.itu.moapd.gocaching.R
import kotlinx.android.synthetic.main.fragment_geo_cache.*
import java.util.*

class EditGeoCacheFragment : Fragment() {
    companion object {
        lateinit var geoCacheDB: GeoCacheDB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geoCacheDB = GeoCacheDB.get(requireActivity())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_geo_cache, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()


// Buttons
        submit_button.setText(R.string.update_button)
        submit_button.setOnClickListener {
            if (editTextCache.text.isNotEmpty() && editTextWhere.text.isNotEmpty()) {
                var date = Date()
                geoCacheDB.updateGeoCache(editTextCache.text.toString(), editTextWhere.text.toString(), date)
                editTextCache.text.clear()
                editTextWhere.text.clear()
                info_text.setText(geoCacheDB.getLastGeoCacheInfo())
            }
        }

    }
}

