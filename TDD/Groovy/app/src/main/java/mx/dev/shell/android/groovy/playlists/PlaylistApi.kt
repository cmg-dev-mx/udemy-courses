package mx.dev.shell.android.groovy.playlists

import retrofit2.http.GET

interface PlaylistApi {

    @GET("playlists")
    suspend fun fetchAllPlaylists(): List<Playlist>

}
