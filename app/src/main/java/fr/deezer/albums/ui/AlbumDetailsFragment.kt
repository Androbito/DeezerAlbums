package fr.deezer.albums.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.airbnb.mvrx.fragmentViewModel
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import fr.deezer.albums.R
import fr.deezer.albums.api.Resource
import fr.deezer.albums.di.GlideApp
import fr.deezer.albums.model.Album
import fr.deezer.albums.viewmodel.AlbumDetailsViewModel
import fr.deezer.albums.viewmodel.AlbumsListViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_album_details.*
import kotlinx.android.synthetic.main.allbum_details_content.*

@Parcelize
data class AlbumDetailArgs(val id: Int, val title: String) : Parcelable

class AlbumDetailsFragment : BaseFragment() {
    private lateinit var observer: Observer<Resource<Album>>
    private val albumDetailsViewModel : AlbumDetailsViewModel by fragmentViewModel()

    override fun invalidate() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_album_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let {
                        GlideApp.with(this).load(resource.data.coverBig)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(album_cover)
                        GlideApp.with(this).load(resource.data.artist.pictureMedium)
                            .placeholder(R.drawable.ic_artist_placeholder)
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
                        requireView().findViewById(android.R.id.content),
                        "There has been an error",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Retry") {

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
        }
        albumDetailsViewModel.fetchAlbumById()
        //(requireActivity() as MainActivity).setSupportActionBar(detail_toolbar)
        //(requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        const val ARG_ITEM_ID = "album_id"
        const val ARG_ITEM_TITLE = "album_title"
    }

}
