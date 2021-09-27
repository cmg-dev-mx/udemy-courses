package mx.dev.shell.android.groovy

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    val image: Int = R.drawable.playlist
)
