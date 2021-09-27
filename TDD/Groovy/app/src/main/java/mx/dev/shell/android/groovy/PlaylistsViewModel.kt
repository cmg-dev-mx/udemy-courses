package mx.dev.shell.android.groovy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistsViewModel: ViewModel() {


    val playlists = MutableLiveData<Result<List<Playlist>>>()
}
