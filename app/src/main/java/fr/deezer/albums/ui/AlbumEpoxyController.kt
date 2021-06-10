package fr.deezer.albums.ui

import android.view.View
import android.widget.AdapterView

import com.airbnb.epoxy.EpoxyController
import fr.deezer.albums.model.Album


class AlbumEpoxyController : EpoxyController() {

    var albums: List<Album> = emptyList()
    var clickListener: ((Album) -> Unit)? = null

    override fun buildModels() {
        for (album in albums) {
            AlbumUiModelModel_().id(album.id)
                .title(album.title)
                .albumPic(album.coverMedium)
                .itemClickListener{
                    _ -> clickListener?.invoke(album)
                }
                .spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount / 3 }
                .addTo(this)
        }

    }

}