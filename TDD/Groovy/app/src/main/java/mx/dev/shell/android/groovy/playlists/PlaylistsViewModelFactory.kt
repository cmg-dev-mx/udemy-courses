package mx.dev.shell.android.groovy.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class PlaylistsViewModelFactory @Inject constructor(
    private val repository: PlaylistRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaylistsViewModel(repository) as T
    }
}
