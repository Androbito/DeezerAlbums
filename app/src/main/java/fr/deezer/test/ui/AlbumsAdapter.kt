package fr.deezer.test.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import fr.deezer.test.R
import fr.deezer.test.di.GlideApp
import fr.deezer.test.model.Album
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumsAdapter(
    private var albums: ArrayList<Album>
) :
    RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Album
            val intent = Intent(v.context, AlbumDetailActivity::class.java).apply {
                putExtra(AlbumDetailActivity.ARG_ITEM_ID, item.id)
                putExtra(AlbumDetailActivity.ARG_ITEM_TITLE,item.title)
            }
            v.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = albums[position]
        holder.titleView.text = item.title
        GlideApp.with(holder.itemView.context).load(item.coverMedium).placeholder(R.drawable.ic_placeholder).error(
            GlideApp
                .with(holder.itemView.context)
                .load("https://pbs.twimg.com/profile_images/1125516322111197187/AKkQkSFq_200x200.png")
        ).transition(DrawableTransitionOptions.withCrossFade().crossFade(1000))
            .into(holder.coverView)
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    fun addData(data: List<Album>) {
        albums.clear()
        albums.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = albums.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.albumTitle
        val coverView: ImageView = view.albumCover
    }
}