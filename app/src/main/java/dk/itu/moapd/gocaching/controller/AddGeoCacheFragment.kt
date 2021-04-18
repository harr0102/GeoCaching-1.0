package dk.itu.moapd.gocaching.controller
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dk.itu.moapd.gocaching.R
import dk.itu.moapd.gocaching.model.GeoCache
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_geo_cache.*
import java.text.SimpleDateFormat
import java.util.*
import io.realm.Realm.getDefaultInstance



class AddGeoCacheFragment : Fragment() {
    var dateFormat: SimpleDateFormat? = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    private lateinit var mRealm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_geo_cache, container, false)
        mRealm = getDefaultInstance()

        return view
    }

    override fun onStart() {
        super.onStart()
// Buttons
        submit_button.setText(R.string.add_cache)
        submit_button.setOnClickListener {
            if (editTextCache.text.isNotEmpty() && editTextWhere.text.isNotEmpty()) {
                val newCache = editTextCache.text.toString()
                val newWhere = editTextWhere.text.toString()
                val newDate = dateFormat?.format(Date()).toString()

                mRealm.executeTransactionAsync { realm ->
                    var id = realm.where(GeoCache::class.java).max("id")
                    if (id == null) id = 0

                    val geoCache = GeoCache(
                        id = id.toInt() + 1,
                        cache = newCache,
                        where = newWhere,
                        dateOfCreation = newDate
                    )

                    realm.insert(geoCache)
                }
                Toast.makeText(requireActivity(), "Added cache: ${newCache}", Toast.LENGTH_SHORT).show()
                editTextCache.text.clear()
                editTextWhere.text.clear()
                //val manager = requireFragmentManager()
                //manager.popBackStack()
            }
        }

    }
}


