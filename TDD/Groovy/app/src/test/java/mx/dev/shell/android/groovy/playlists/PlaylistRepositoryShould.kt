package mx.dev.shell.android.groovy.playlists

import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

class PlaylistRepositoryShould: BaseUnitTest() {

    private val service = mock(PlaylistService::class.java)

    @Test
    fun getPlaylistsFromService() = runBlockingTest {
        val repository = PlaylistRepository(service)

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

}