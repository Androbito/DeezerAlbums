package fr.deezer.albums.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.*
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import fr.deezer.albums.R
import fr.deezer.albums.di.GlideApp
import fr.deezer.albums.model.Album
import kotlinx.android.synthetic.main.item_album.view.*
import java.util.jar.Attributes

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class AlbumUiModel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private val titleView: TextView
    private val albumCover: ImageView


    init {
        inflate(context, R.layout.item_album, this)
        albumCover = findViewById(R.id.albumCover)
        titleView = findViewById(R.id.albumTitle)
    }

    @ModelProp
    fun setAlbumPic(imgUrl: String?) {
        GlideApp.with(albumCover).load(imgUrl).placeholder(R.drawable.ic_placeholder).error(
            GlideApp
                .with(albumCover)
                .load("https://pbs.twimg.com/profile_images/1125516322111197187/AKkQkSFq_200x200.png")
        ).transition(DrawableTransitionOptions.withCrossFade().crossFade(1000))
            .into(albumCover)
    }

    @TextProp
    fun setTitle(title: CharSequence?) {
        titleView.text = title
    }

    @CallbackProp // Use this annotation for click listeners or other callbacks.
    fun setItemClickListener(listener: OnClickListener?) {
        rootView.setOnClickListener(listener)
    }



}