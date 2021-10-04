package mx.dev.shell.android.groovy.playlists

import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val service: PlaylistService
) {

    suspend fun getPlaylists() = service.fetchPlaylists()
}
