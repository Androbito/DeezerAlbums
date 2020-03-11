package fr.deezer.test.repository

import androidx.lifecycle.LiveData
import fr.deezer.test.api.ApiService
import fr.deezer.test.api.NetworkBoundResource
import fr.deezer.test.api.Resource
import fr.deezer.test.db.AlbumDao
import fr.deezer.test.model.Album
import fr.deezer.test.model.AlbumsResponse
import fr.deezer.test.api.AppExecutors
import fr.deezer.test.api.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepository @Inject constructor(
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
