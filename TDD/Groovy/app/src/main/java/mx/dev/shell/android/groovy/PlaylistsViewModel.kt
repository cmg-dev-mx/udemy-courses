package mx.dev.shell.android.groovy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistsViewModel(repository: PlaylistRepository) : ViewModel() {

    val playlists = MutableLiveData<Result<List<Playlist>>>()

    init {
        repository.getPlaylists()
    }
}
