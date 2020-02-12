package fr.deezer.test.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import fr.deezer.test.R
import fr.deezer.test.api.Resource
import fr.deezer.test.calculateNoOfColumns
import fr.deezer.test.model.Album
import fr.deezer.test.viewmodel.AlbumViewModel
import kotlinx.android.synthetic.main.activity_albums_list.*


class AlbumsListActivity : AppCompatActivity() {

    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var viewAdapter: AlbumsAdapter
    private var albumList = ArrayList<Album>()
    private lateinit var observer: Observer<Resource<List<Album>>>
    private var pageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel::class.java)
        setupRecyclerView(this, item_list)
        observer = Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { albums -> viewAdapter.addData(albums) }
                }
                Resource.Status.LOADING -> {
                    Log.i("Loading", "...")
                }
                Resource.Status.ERROR -> {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "There has been an error",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Retry") {
                            albumViewModel.fetchAlbums(pageIndex).observe(this, observer)
                        }
                        .setActionTextColor(
                            ContextCompat.getColor(
                                this,
                                android.R.color.holo_red_light
                            )
                        )
                        .show()
                }
            }
        }
        albumViewModel.fetchAlbums(pageIndex).observe(this, observer)
    }

    private fun setupRecyclerView(
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
    }


}

