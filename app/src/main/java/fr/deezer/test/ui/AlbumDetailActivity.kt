package fr.deezer.test.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import fr.deezer.test.R
import fr.deezer.test.api.Resource
import fr.deezer.test.di.GlideApp
import fr.deezer.test.model.Album
import fr.deezer.test.viewmodel.AlbumViewModel
import kotlinx.android.synthetic.main.activity_album_details.*
import kotlinx.android.synthetic.main.allbum_details_content.*


class AlbumDetailActivity : AppCompatActivity() {
    private lateinit var observer: Observer<Resource<Album>>
    private lateinit var albumViewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)
        albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel::class.java)
        observer = Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let {
                        GlideApp.with(this).load(resource.data.coverBig).transition(DrawableTransitionOptions.withCrossFade())
                            .into(album_cover)
                        GlideApp.with(this).load(resource.data.artist.pictureMedium).placeholder(R.drawable.ic_artist_placeholder)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(artist_picture)
                        artist_name.text = "by ${resource.data.artist.name}"
                        album_title.text = resource.data.title
                        album_link.text = "Check it on ${resource.data.link}"
                        album_date.text = "Released on : ${resource.data.releaseDate}"
                    }
                }
                Resource.Status.ERROR -> {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "There has been an error",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Retry") {

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
        albumViewModel.albumById(intent.getIntExtra(ARG_ITEM_ID, -1)).observe(this, observer)
        detail_toolbar.title = intent.getStringExtra(ARG_ITEM_TITLE)
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        const val ARG_ITEM_ID = "album_id"
        const val ARG_ITEM_TITLE = "album_title"
    }

}
