package mx.dev.shell.android.groovy.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData

class PlaylistsViewModel(repository: PlaylistRepository) : ViewModel() {

    val playlists = liveData {
        emitSource(repository.getPlaylists().asLiveData())
    }
}
