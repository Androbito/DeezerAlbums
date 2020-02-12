package fr.deezer.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import fr.deezer.test.DeezerApp
import fr.deezer.test.api.Constants
import fr.deezer.test.api.Resource
import fr.deezer.test.model.Album
import fr.deezer.test.repository.AlbumsRepository
import javax.inject.Inject

class AlbumViewModel : ViewModel() {

    @Inject
    lateinit var albumsRepository: AlbumsRepository

    fun fetchAlbums(pageIndex: Int): LiveData<Resource<List<Album>>> {
        return albumsRepository.getAlbums(Constants.USER_ID, pageIndex*25)
    }

    fun albumById(albumId: Int): LiveData<Resource<Album>>{
        return albumsRepository.getAlbumById(albumId)
    }

    init {
        DeezerApp.appComponent.inject(this)
    }
}