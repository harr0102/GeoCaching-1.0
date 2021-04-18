package dk.itu.moapd.gocaching.controller
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.itu.moapd.gocaching.R
import dk.itu.moapd.gocaching.model.GeoCache
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_go_caching.*
import kotlinx.android.synthetic.main.fragment_go_caching.*

private const val TAG = "GoCachingFragment"
class GoCachingFragment : Fragment() {

    private lateinit var mRealm: Realm
    private lateinit var adapter: GeoCacheAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_go_caching, container, false)
        mRealm = Realm.getDefaultInstance()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // List geocaches click event
        val results = mRealm.where(GeoCache::class.java)
            .sort("id", Sort.ASCENDING).findAll()

        geo_cache_recycler_view.layoutManager = LinearLayoutManager(context)
        geo_cache_recycler_view.adapter = GeoCacheAdapter(results)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_go_caching, menu)
    }

    /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if (item.itemId == R.id.action_logout) {
             user = app.currentUser()
             user?.logOut()
             return true
         }
         return super.onOptionsItemSelected(item)
     }*/


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
         val results = mRealm.where(GeoCache::class.java)
             .sort("id", Sort.ASCENDING).findAll()
             geo_cache_recycler_view.adapter = GeoCacheAdapter(results)
     }
 }

    private inner class GeoCacheAdapter(data: OrderedRealmCollection<GeoCache>) :
        RealmRecyclerViewAdapter<GeoCache, GeoCacheAdapter.GeoCacheViewHolder>(data, true) {
        private var selectedPos = RecyclerView.NO_POSITION

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): GeoCacheViewHolder {
            val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_geo_cache, parent, false)
            return GeoCacheViewHolder(layout)
        }

        override fun onBindViewHolder(holder: GeoCacheViewHolder, position: Int) {
            val geoCache = getItem(position)
            holder.apply {
                cache?.text = geoCache?.cache
                where?.text = geoCache?.where
                date?.text = geoCache?.dateOfCreation
                updatedDate?.text = geoCache?.dateUpdated
                itemView.isSelected = selectedPos == position
                itemView.setOnClickListener {

                    if(it != null){ // This is where the delete is performed when clicking on the recyclerview
                        val manager = requireFragmentManager()
                        val fragment = EditGeoCacheFragment()
                        fragment.setGeoCache(geoCache!!)
                        val currentFragment = manager.findFragmentById((R.id.fragment_container))
                        manager
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(currentFragment?.tag)
                            .commit()
                    }
                    notifyItemChanged(selectedPos)
                    selectedPos = layoutPosition
                    notifyItemChanged(selectedPos)
                }
            }
        }
        inner class GeoCacheViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val cache: TextView = view.findViewById(R.id.cache_text)
            val where: TextView = view.findViewById(R.id.where_text)
            val date: TextView = view.findViewById(R.id.date_text)
            val updatedDate: TextView = view.findViewById(R.id.updatedDate_text)
        }
 }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}

