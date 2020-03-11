package fr.deezer.test.di

import com.google.gson.Gson
import dagger.Component
import fr.deezer.test.repository.AlbumsRepository
import fr.deezer.test.viewmodel.AlbumViewModel
import fr.deezer.test.api.AppExecutors
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class, ApiServiceModule::class]
)
interface AppComponent {

    val gson: Gson

    val albumsRepository: AlbumsRepository

    val appExecutor: AppExecutors

    fun inject(viewModel: AlbumViewModel)

}