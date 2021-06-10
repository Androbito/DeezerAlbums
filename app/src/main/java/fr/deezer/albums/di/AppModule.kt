package fr.deezer.albums.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import fr.deezer.albums.api.ApiService
import fr.deezer.albums.api.AppExecutors
import fr.deezer.albums.api.Constants
import fr.deezer.albums.api.LiveDataCallAdapterFactory
import fr.deezer.albums.db.AlbumDao
import fr.deezer.albums.db.AlbumDb
import fr.deezer.albums.repository.AlbumsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object AppModule {

    val appModule = module {
        single { provideRepository(get(), get()) }
        single { provideAlbumDao(get()) }
        single { provideDb(get()) }
        single { provideGson() }
        single { provideApiService() }
    }

    private fun provideApiService(): ApiService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(20, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(httpClient.build())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun provideDb(app: Application): AlbumDb {
        return Room.databaseBuilder(app, AlbumDb::class.java, "albums_database")
            .fallbackToDestructiveMigration().build()
    }

    private fun provideAlbumDao(db: AlbumDb): AlbumDao {
        return db.albumDao()
    }

    private fun provideRepository(apiService: ApiService, db: AlbumDao): AlbumsRepository {
        return AlbumsRepository(apiService, db, AppExecutors())
    }

    private fun provideGson(): Gson {
        return Gson()
    }


}