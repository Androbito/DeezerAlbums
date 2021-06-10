package fr.deezer.albums.db

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.deezer.albums.model.Album


@Dao
abstract class AlbumDao {

    @Query("SELECT * FROM album")
    abstract fun getAll(): LiveData<List<Album>>

    @Query("SELECT * FROM album WHERE id = :id LIMIT 1")
    abstract fun getAlbumById(id:Int) : LiveData<Album>

    @Query("DELETE FROM album")
    abstract fun nukeTable()

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg album: Album)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(albumList: List<Album>)

//    fun insertAlbum(album: Album){
//        album.idArtist = album.artist.id
//        insert(album)
//    }
//
//    fun insertAlbumList(albumList: List<Album>){
//        val entities = ArrayList<Album>(albumList.size)
//        albumList.forEach { it.idArtist = it.artist.id }
//        insert(entities)
//    }
}