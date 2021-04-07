package dk.itu.moapd.gocaching
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_go_caching.*
import kotlinx.android.synthetic.main.fragment_go_caching.*

class GoCachingFragment : Fragment() {

    companion object {
        lateinit var geoCacheDB: GeoCacheDB
        lateinit var adapter: GeoCacheArrayAdapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geoCacheDB = GeoCacheDB.get(requireActivity())
        val geoCaches = geoCacheDB.getGeoCaches()
        adapter = GeoCacheArrayAdapter(requireActivity(), geoCaches)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_go_caching, container, false)
    }

    override fun onStart() {
        super.onStart()

        add_button.setOnClickListener {
            val manager = requireFragmentManager()
            val fragment = AddGeoCacheFragment()

            manager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(fragment_container?.tag.toString())
                .commit()
        }

        edit_button.setOnClickListener {
            val manager = requireFragmentManager()
            val fragment = EditGeoCacheFragment()
            val currentFragment = manager.findFragmentById((R.id.fragment_container))
            manager
                .beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(currentFragment?.tag)
                .commit()
        }

        list_cache_button.setOnClickListener {
            // Create the adapter
            geo_cache_list_view.adapter = adapter
        }
    }


}
