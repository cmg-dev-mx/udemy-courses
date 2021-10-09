package mx.dev.shell.android.groovy.playlistDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistDetailViewModel(private val service: PlaylistDetailService): ViewModel() {

    val playlistDetail: MutableLiveData<Result<PlaylistDetail>> = MutableLiveData()

    fun getPlaylistDetail(id: String) {
        service.fetchPlaylistDetail(id)
    }
}
