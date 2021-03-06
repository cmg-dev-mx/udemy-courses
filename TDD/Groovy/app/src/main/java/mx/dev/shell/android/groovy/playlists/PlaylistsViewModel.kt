package mx.dev.shell.android.groovy.playlists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.onEach

class PlaylistsViewModel(repository: PlaylistRepository) : ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val playlists = liveData {
        loader.postValue(true)
        emitSource(repository.getPlaylists()
            .onEach {
                loader.postValue(false)
            }.asLiveData())
    }
}
