package fr.deezer.test.api

import androidx.lifecycle.LiveData
import fr.deezer.test.model.Album
import fr.deezer.test.model.AlbumsResponse
import fr.wawashi.wawapay.services.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("user/{id}/albums")
    fun getAlbums(@Path("id") id: Long,@Query("index") page: Int): LiveData<ApiResponse<AlbumsResponse>>

    @GET("album/{id}")
    fun getAlbumById(id: Int): LiveData<ApiResponse<Album>>

}