package mx.dev.shell.android.groovy.playlists

import junit.framework.TestCase.assertEquals
import mx.dev.shell.android.groovy.R
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import org.junit.Test

class PlaylistMapperShould : BaseUnitTest() {

    private val mapper = PlaylistMapper()

    private val playlistRaw = PlaylistRaw("1", "name", "cat")
    private val playlists = mapper(listOf(playlistRaw))
    private val playlist = playlists[0]

    private val playlistRockRaw = PlaylistRaw("1", "name", "rock")
    private val playlistsRock = mapper(listOf(playlistRockRaw))
    private val playlistRock = playlistsRock[0]

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.drawable.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.drawable.rock, playlistRock.image)
    }
}