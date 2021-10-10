package mx.dev.shell.android.groovy.playlists

import mx.dev.shell.android.groovy.R
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {

    override fun invoke(p1: List<PlaylistRaw>): List<Playlist> {
        return p1.map {
            val image = when(it.category) {
                "rock" -> R.drawable.rock
                else -> R.drawable.playlist
            }

            Playlist(it.id, it.name, it.category, image)
        }
    }

}
