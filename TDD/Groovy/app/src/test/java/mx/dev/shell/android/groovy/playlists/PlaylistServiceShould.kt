package mx.dev.shell.android.groovy.playlists

import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

class PlaylistServiceShould : BaseUnitTest() {

    private val api = mock(PlaylistApi::class.java)

    @Test
    fun fetchPlaylistFromApi() = runBlockingTest {
        val service = PlaylistService(api)
        service.fetchPlaylists()
        verify(api, times(1)).fetchAllPlaylists()
    }
}