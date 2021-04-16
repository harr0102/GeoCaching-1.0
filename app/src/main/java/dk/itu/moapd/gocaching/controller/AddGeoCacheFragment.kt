package dk.itu.moapd.gocaching.controller
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dk.itu.moapd.gocaching.R
import dk.itu.moapd.gocaching.model.GeoCache
import kotlinx.android.synthetic.main.fragment_geo_cache.*
import kotlinx.android.synthetic.main.list_geo_cache.*
import java.text.SimpleDateFormat
import java.util.*


class AddGeoCacheFragment : Fragment() {
    var dateFormat: SimpleDateFormat? = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

    companion object {
        lateinit var geoCacheVM: GeoCacheVM

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geoCacheVM = ViewModelProviders.of(this).get(GeoCacheVM::class.java)
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
        submit_button.setText(R.string.add_cache)
        submit_button.setOnClickListener {
            if (editTextCache.text.isNotEmpty() && editTextWhere.text.isNotEmpty()) {
                geoCacheVM.add(GeoCache(cache = editTextCache.text.toString(), where = editTextWhere.text.toString(), dateCreated = dateFormat?.format(Date()).toString()))
                Toast.makeText(requireActivity(), "Added cache: ${editTextCache.text}", Toast.LENGTH_SHORT).show()
                editTextCache.text.clear()
                editTextWhere.text.clear()
                //val manager = requireFragmentManager()
                //manager.popBackStack()
            }
        }

    }
}


