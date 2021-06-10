package fr.deezer.albums.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import fr.deezer.albums.R
import fr.deezer.albums.api.Resource
import fr.deezer.albums.model.Album
import fr.deezer.albums.viewmodel.AlbumsListViewModel
import kotlinx.android.synthetic.main.activity_albums_list.*


class AlbumsListFragment : BaseFragment() {

    private lateinit var controller: AlbumEpoxyController
    private val albumViewModel: AlbumsListViewModel by fragmentViewModel()

    private lateinit var observer: Observer<Resource<List<Album>>>
    private var pageIndex = 0

    override fun invalidate() = withState(albumViewModel) { state ->
        state.albums.invoke()?.let {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { albums ->
                        controller.albums = albums
                    }
                }
                Resource.Status.LOADING -> {
                    Log.i("Loading", "...")
                }
                Resource.Status.ERROR -> {
                    Log.i("Error", "...")
                }

            }
        }
        controller.clickListener = { album ->
            navigateTo(
                R.id.action_albumsListFragment_to_albumDetailsFragment,
                AlbumDetailArgs(album.id, album.title)
            )
        }
        controller.requestModelBuild()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_albums_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewManager = GridLayoutManager(requireContext(), 3)
        controller = AlbumEpoxyController()
        controller.spanCount = 3
        viewManager.spanSizeLookup = controller.spanSizeLookup;
        item_list.layoutManager = viewManager
        item_list.setController(controller)
        /*observer = Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { albums ->

                    }
                }
                Resource.Status.LOADING -> {
                    Log.i("Loading", "...")
                }
                Resource.Status.ERROR -> {
                    Snackbar.make(
                        view.findViewById(android.R.id.content),
                        "There has been an error",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Retry") {
                            albumViewModel.fetchAlbums(pageIndex)
                        }
                        .setActionTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                android.R.color.holo_red_light
                            )
                        )
                        .show()
                }
            }
        }*/
        albumViewModel.fetchAlbums(0)
    }

    /*private fun setupRecyclerView(
        lifecycleOwner: LifecycleOwner,
        recyclerView: RecyclerView
    ) {
        val viewManager = GridLayoutManager(this, this.calculateNoOfColumns(120f))
        viewAdapter = AlbumsAdapter(albumList)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == viewAdapter.itemCount - 1) {
                    pageIndex++
                    albumViewModel.fetchAlbums(pageIndex).observe(lifecycleOwner, observer)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }*/

}

