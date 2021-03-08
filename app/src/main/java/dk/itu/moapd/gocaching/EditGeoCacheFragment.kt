package dk.itu.moapd.gocaching
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_geo_cache.*

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
                geoCacheDB.updateGeoCache(editTextCache.text.toString(), editTextWhere.toString())
                editTextCache.text.clear()
                editTextWhere.text.clear()
                info_text.setText(geoCacheDB.getLastGeoCacheInfo())
            }
        }

    }
}


