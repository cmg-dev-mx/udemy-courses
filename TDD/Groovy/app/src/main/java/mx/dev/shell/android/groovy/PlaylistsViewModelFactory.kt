package mx.dev.shell.android.groovy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlaylistsViewModelFactory(
    private val repository: PlaylistRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaylistsViewModel(repository) as T
    }
}
