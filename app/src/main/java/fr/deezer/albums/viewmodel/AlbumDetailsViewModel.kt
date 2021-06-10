package fr.deezer.albums.viewmodel

import androidx.lifecycle.*
import com.airbnb.mvrx.*
import fr.deezer.albums.model.Album
import fr.deezer.albums.repository.AlbumsRepository
import fr.deezer.albums.ui.AlbumDetailArgs
import io.reactivex.Observable
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class AlbumDetailState(
    val id: Int,
    val title: String,
    val album: Async<Album> = Uninitialized
) : MvRxState {
    constructor(args: AlbumDetailArgs) : this(args.id, args.title)
}

class AlbumDetailsViewModel(
    initialState: AlbumDetailState,
    private var albumsRepository: AlbumsRepository
) :
    BaseViewModel<AlbumDetailState>(initialState) {

    private fun albumById(id: Int) {
        Observable.fromPublisher(
            LiveDataReactiveStreams.toPublisher(
                object : LifecycleOwner {
                    override fun getLifecycle(): Lifecycle {
                        return object : Lifecycle() {
                            override fun addObserver(observer: LifecycleObserver) = Unit

                            override fun removeObserver(observer: LifecycleObserver) = Unit

                            override fun getCurrentState(): State {
                                return State.STARTED
                            }

                        }
                    }

                },
                albumsRepository.getAlbumById(id)
            )
        ).execute {
            copy(album = this.album)
        }
    }

    fun fetchAlbumById() = withState { state: AlbumDetailState ->
        albumById(state.id)
    }

    companion object : MvRxViewModelFactory<AlbumDetailsViewModel, AlbumDetailState>,
        KoinComponent {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: AlbumDetailState
        ): AlbumDetailsViewModel? {

            return AlbumDetailsViewModel(
                state, getKoin().get()
            )
        }
    }
}