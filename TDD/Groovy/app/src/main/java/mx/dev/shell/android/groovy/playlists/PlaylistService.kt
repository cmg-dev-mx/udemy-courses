package mx.dev.shell.android.groovy.playlists

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistService(val api: PlaylistApi) {

    suspend fun fetchPlaylists(): Flow<Result<List<Playlist>>> {
        api.fetchAllPlaylists()

        return flow {  }
    }
}
