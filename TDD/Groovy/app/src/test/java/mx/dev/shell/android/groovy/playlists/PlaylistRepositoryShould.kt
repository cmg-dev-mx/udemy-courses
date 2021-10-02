package mx.dev.shell.android.groovy.playlists

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

class PlaylistRepositoryShould: BaseUnitTest() {

    private val service = mock(PlaylistService::class.java)
    private val playlists: List<Playlist> = mock(List::class.java) as List<Playlist>

    @Test
    fun getPlaylistsFromService() = runBlockingTest {
        val repository = PlaylistRepository(service)

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitPlaylistsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlists))
            }
        )

        return PlaylistRepository(service)
    }
}