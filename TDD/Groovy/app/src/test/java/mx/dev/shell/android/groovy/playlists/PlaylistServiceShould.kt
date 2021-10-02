package mx.dev.shell.android.groovy.playlists

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

class PlaylistServiceShould : BaseUnitTest() {

    private val playlists: List<Playlist> = mock(List::class.java) as List<Playlist>
    private val api = mock(PlaylistApi::class.java)

    @Test
    fun fetchPlaylistFromApi() = runBlockingTest {
        val service = PlaylistService(api)
        service.fetchPlaylists()
        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        val service = mockSuccessfulCase()
        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    private fun mockSuccessfulCase(): PlaylistService {
        `when`(api.fetchAllPlaylists()).thenReturn(playlists)

        val service = PlaylistService(api)
        return service
    }
}