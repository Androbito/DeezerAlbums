package fr.deezer.albums.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import fr.deezer.albums.api.ApiService
import fr.deezer.albums.api.NetworkBoundResource
import fr.deezer.albums.api.Resource
import fr.deezer.albums.db.AlbumDao
import fr.deezer.albums.model.Album
import fr.deezer.albums.model.AlbumsResponse
import fr.deezer.albums.api.AppExecutors
import fr.deezer.albums.api.ApiResponse

class AlbumsRepository constructor(
    private val apiService: ApiService,
    private val albumDao: AlbumDao,
    private val appExecutors: AppExecutors = AppExecutors()
) {
    fun getAlbums(userId: Long, pageIndex: Int): LiveData<Resource<List<Album>>> =
        object : NetworkBoundResource<List<Album>, AlbumsResponse>(appExecutors) {
            override fun saveCallResult(item: AlbumsResponse) {
                albumDao.insert(item.data)
            }

            override fun shouldFetch(data: List<Album>?): Boolean {
                return data == null || data.size<=pageIndex
            }

            override fun loadFromDb(): LiveData<List<Album>> {
                return albumDao.getAll()
            }

            override fun createCall(): LiveData<ApiResponse<AlbumsResponse>> {
                return apiService.getAlbums(userId,pageIndex)
            }
        }.asLiveData()

    fun getAlbumById(albumId: Int): LiveData<Resource<Album>> =
        object : NetworkBoundResource<Album,Album>(appExecutors) {
            override fun saveCallResult(item: Album) {
                albumDao.insert(item)
            }

            override fun shouldFetch(data: Album?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<Album> {
                return albumDao.getAlbumById(albumId)
            }

            override fun createCall(): LiveData<ApiResponse<Album>> {
                return apiService.getAlbumById(albumId)
            }

        }.asLiveData()
}
