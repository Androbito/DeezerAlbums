package fr.deezer.test.di

import com.google.gson.Gson
import dagger.Component
import dagger.android.AndroidInjectionModule
import fr.deezer.test.DeezerApp
import fr.deezer.test.repository.AlbumsRepository
import fr.deezer.test.viewmodel.AlbumViewModel
import fr.wawashi.wawapay.AppExecutors
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