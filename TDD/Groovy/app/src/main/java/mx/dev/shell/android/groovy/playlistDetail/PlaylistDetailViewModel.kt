package mx.dev.shell.android.groovy.playlistDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistDetailViewModel: ViewModel() {

    val playlistDetail: MutableLiveData<Result<PlaylistDetail>> = MutableLiveData()

    fun getPlaylistDetail(id: String) {
        TODO("Not yet implemented")
    }
}
