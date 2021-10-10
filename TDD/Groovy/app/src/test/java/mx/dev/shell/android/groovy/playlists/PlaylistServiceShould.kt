package mx.dev.shell.android.groovy.playlists

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PlaylistServiceShould : BaseUnitTest() {

    private val playlists: List<PlaylistRaw> = mock(List::class.java) as List<PlaylistRaw>
    private val api = mock(PlaylistApi::class.java)

    @Test
    fun fetchPlaylistFromApi() = runBlockingTest {
        val service = PlaylistService(api)
        service.fetchPlaylists().first()
        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        val service = mockSuccessfulCase()
        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        val service = mockFailureCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockFailureCase(): PlaylistService {
        `when`(api.fetchAllPlaylists()).thenThrow(RuntimeException("Damn backend developers"))

        return PlaylistService(api)
    }

    private suspend fun mockSuccessfulCase(): PlaylistService {
        `when`(api.fetchAllPlaylists()).thenReturn(playlists)

        return PlaylistService(api)
    }
}