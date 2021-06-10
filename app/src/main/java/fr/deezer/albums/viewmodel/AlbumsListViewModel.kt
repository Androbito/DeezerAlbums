package fr.deezer.albums.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.airbnb.mvrx.*
import fr.deezer.albums.api.Constants
import fr.deezer.albums.api.Resource
import fr.deezer.albums.model.Album
import fr.deezer.albums.repository.AlbumsRepository
import io.reactivex.Observable
import org.koin.core.KoinComponent

data class AlbumsListState(
    val albums: Async<Resource<List<Album>>> = Uninitialized
) : MvRxState

class AlbumsListViewModel(
    initialState: AlbumsListState,
    private val albumsRepository: AlbumsRepository,
    val lifecycleOwner: LifecycleOwner
) :
    BaseViewModel<AlbumsListState>(initialState) {
    fun albums(pageIndex: Int): Observable<Resource<List<Album>>> {
        return Observable.fromPublisher(
            LiveDataReactiveStreams.toPublisher(
                lifecycleOwner,
                albumsRepository.getAlbums(Constants.USER_ID, pageIndex * 25)
            )
        )
    }

    fun fetchAlbums(pageIndex: Int) = withState { state ->
        if (state.albums is Loading) return@withState
        albums(pageIndex).execute {
            copy(albums = it)
        }
    }

    companion object : MvRxViewModelFactory<AlbumsListViewModel, AlbumsListState>,
        KoinComponent {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: AlbumsListState
        ): AlbumsListViewModel? {
            viewModelContext.activity
            return AlbumsListViewModel(
                state, getKoin().get(),viewModelContext.activity
            )
        }
    }

}