package mx.dev.shell.android.groovy.playlists

import mx.dev.shell.android.groovy.playlistDetail.PlaylistDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistApi {

    @GET("playlists")
    suspend fun fetchAllPlaylists(): List<PlaylistRaw>

    @GET("playlist-details/{id}")
    suspend fun fetchPlaylistById(@Path("id") id: String): PlaylistDetail

}
