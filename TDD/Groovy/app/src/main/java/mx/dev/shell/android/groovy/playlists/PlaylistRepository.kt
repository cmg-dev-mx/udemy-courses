package mx.dev.shell.android.groovy.playlists

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepository(val service: PlaylistService) {

    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> {
        service.fetchPlaylists()

        return flow {}
    }
}
