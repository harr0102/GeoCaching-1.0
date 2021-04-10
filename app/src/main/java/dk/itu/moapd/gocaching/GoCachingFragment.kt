package dk.itu.moapd.gocaching
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_go_caching.*
import kotlinx.android.synthetic.main.fragment_go_caching.*

class GoCachingFragment : Fragment() {
    private var adapter: GeoCacheAdapter? = null

    companion object {
        lateinit var geoCacheDB: GeoCacheDB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geoCacheDB = GeoCacheDB.get(requireActivity())
        val geoCaches = geoCacheDB.getGeoCaches()
        adapter = GeoCacheAdapter(geoCaches)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_go_caching, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // List geocaches click event
        geo_cache_recycler_view.layoutManager = LinearLayoutManager(context)
        geo_cache_recycler_view.adapter = adapter

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
            geo_cache_recycler_view.adapter = adapter
        }
    }

    private inner class GeoCacheAdapter(
            var geoCaches: List<GeoCache>) :
            RecyclerView.Adapter<GeoCacheViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): GeoCacheViewHolder {
            val layout = layoutInflater
                    .inflate(R.layout.list_geo_cache, parent, false)
            return GeoCacheViewHolder(layout)
        }
        override fun getItemCount() = geoCaches.size
        override fun onBindViewHolder(holder: GeoCacheViewHolder,
                                      position: Int) {
            val geoCache = geoCaches[position]
            holder.apply {
                cache.text = geoCache.cache
                where.text = geoCache.where
                date.text = geoCache.date
            }
        }
    }

    private inner class GeoCacheViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
        val cache: TextView = view.findViewById(R.id.cache_text)
        val where: TextView = view.findViewById(R.id.where_text)
        val date: TextView = view.findViewById(R.id.date_text)
    }


}

