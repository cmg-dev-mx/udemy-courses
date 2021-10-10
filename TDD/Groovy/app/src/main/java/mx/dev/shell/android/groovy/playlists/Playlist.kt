package mx.dev.shell.android.groovy.playlists

import mx.dev.shell.android.groovy.R

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    val image: Int = R.drawable.playlist
)
