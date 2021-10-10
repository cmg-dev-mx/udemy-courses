package mx.dev.shell.android.groovy.playlistDetail

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.playlists.PlaylistApi
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PlaylistDetailServiceShould: BaseUnitTest() {

    private val api = mock(PlaylistApi::class.java)
    private val playlistDetail = mock(PlaylistDetail::class.java)
    private val id = "1"

    @Test
    fun fetchPlaylistDetailFromApi() = runBlockingTest {
        val service = mockSuccessfulCase()

        service.fetchPlaylistDetail(id).single()
        verify(api, times(1)).fetchPlaylistById(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {
        val service = mockSuccessfulCase()

        assertEquals(Result.success(playlistDetail), service.fetchPlaylistDetail(id).single())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        val service = mockFailureCase()

        assertEquals("Something went wrong", service.fetchPlaylistDetail(id).first().exceptionOrNull()?.message)
    }

    private suspend fun mockFailureCase(): PlaylistDetailService {
        `when`(api.fetchPlaylistById(id)).thenThrow(RuntimeException("Damn backend developers"))
        return PlaylistDetailService(api)
    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailService {
        `when`(api.fetchPlaylistById(id)).thenReturn(playlistDetail)
        return PlaylistDetailService(api)
    }
}