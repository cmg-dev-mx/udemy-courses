package mx.dev.shell.android.groovy.playlistDetail

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistDetailService @Inject constructor() {

    suspend fun fetchPlaylistDetail(id: String): Flow<Result<PlaylistDetail>> {
        TODO("Not yet implemented")
    }
}
