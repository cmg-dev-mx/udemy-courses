package mx.dev.shell.android.groovy.playlists

class PlaylistRepository(val service: PlaylistService) {

    suspend fun getPlaylists() = service.fetchPlaylists()
}
