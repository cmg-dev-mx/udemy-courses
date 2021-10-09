package mx.dev.shell.android.groovy.playlistDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistDetailViewModel(private val service: PlaylistDetailService): ViewModel() {

    val playlistDetail: MutableLiveData<Result<PlaylistDetail>> = MutableLiveData()

    fun getPlaylistDetail(id: String) {
        viewModelScope.launch {
            service.fetchPlaylistDetail(id)
                .collect {
                    playlistDetail.postValue(it)
                }
        }
    }
}
