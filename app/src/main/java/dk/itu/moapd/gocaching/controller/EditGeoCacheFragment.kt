package dk.itu.moapd.gocaching.controller
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dk.itu.moapd.gocaching.R
import dk.itu.moapd.gocaching.model.GeoCache
import dk.itu.moapd.gocaching.model.GeoCacheVM
import kotlinx.android.synthetic.main.fragment_geo_cache.*
import kotlinx.android.synthetic.main.list_geo_cache.*
import java.text.SimpleDateFormat
import java.util.*

class EditGeoCacheFragment : Fragment() {

    private lateinit var currentGeoCache: GeoCache
    var dateFormat: SimpleDateFormat? = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")


    companion object {
        lateinit var geoCacheVM: GeoCacheVM
    }

    fun setGeoCache(geoCache: GeoCache) {
        this.currentGeoCache = geoCache
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
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_geo_cache, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_cache -> {
                geoCacheVM.remove(currentGeoCache!!)
                val manager = requireFragmentManager()
                manager.popBackStack()
                Toast.makeText(context, "${currentGeoCache.cache} deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onStart() {
        super.onStart()

        //cache_text.setText(R.string.cache_textField)
        //editTextCache.hint = currentGeoCache.cache

        //where_text.setText(R.string.where_textField)
        //editTextWhere.setHint(currentGeoCache.where)

        submit_button.setText(R.string.update_button)
        submit_button.setOnClickListener {
            if (editTextCache.text.isNotEmpty() && editTextWhere.text.isNotEmpty()) {
                val editedCacheText = editTextCache.text.toString()
                val editedWhereText = editTextWhere.text.toString()

                currentGeoCache.cache = editedCacheText
                currentGeoCache.where = editedWhereText
                currentGeoCache.dateEdited = dateFormat?.format(Date()).toString()

                geoCacheVM.edit(currentGeoCache)
                Toast.makeText(requireActivity(), "Cache updated to ${currentGeoCache.cache}", Toast.LENGTH_SHORT).show()
                //val manager = requireFragmentManager()
                //manager.popBackStack()
        }


        }

    }
}


