package mx.dev.shell.android.groovy.playlistDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlaylistDetailViewModel(private val service: PlaylistDetailService): ViewModel() {

    val playlistDetail: MutableLiveData<Result<PlaylistDetail>> = MutableLiveData()

    val loader = MutableLiveData<Boolean>()

    fun getPlaylistDetail(id: String) {
        loader.postValue(true)
        viewModelScope.launch {
            service.fetchPlaylistDetail(id)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    playlistDetail.postValue(it)
                }
        }
    }
}
