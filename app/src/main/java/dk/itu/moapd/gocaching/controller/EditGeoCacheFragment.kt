package dk.itu.moapd.gocaching.controller
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import dk.itu.moapd.gocaching.R
import dk.itu.moapd.gocaching.model.GeoCache
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_geo_cache.*
import java.text.SimpleDateFormat
import java.util.*

class EditGeoCacheFragment : Fragment() {

    private lateinit var selectedGeoCache: GeoCache
    private lateinit var mRealm: Realm
    var dateFormat: SimpleDateFormat? = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")




    fun setGeoCache(geoCache: GeoCache) {
        this.selectedGeoCache = geoCache
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_geo_cache, container, false)
        setHasOptionsMenu(true)
        mRealm = Realm.getDefaultInstance()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_geo_cache, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_cache -> {
                remove(selectedGeoCache)
                val manager = requireFragmentManager()
                manager.popBackStack()
                Toast.makeText(context, "${selectedGeoCache.cache} deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun remove(geoCache: GeoCache) {
        mRealm.executeTransactionAsync {
            geoCache?.deleteFromRealm()
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

                selectedGeoCache.cache = editedCacheText
                selectedGeoCache.where = editedWhereText
                selectedGeoCache.dateUpdated = dateFormat?.format(Date()).toString()

                mRealm.executeTransactionAsync { realm ->
                    realm.copyToRealm(selectedGeoCache!!)
                }
                Toast.makeText(requireActivity(), "Cache updated to ${selectedGeoCache.cache}", Toast.LENGTH_SHORT).show()
                //val manager = requireFragmentManager()
                //manager.popBackStack()
        }


        }

    }
}


