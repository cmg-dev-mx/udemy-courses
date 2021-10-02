package mx.dev.shell.android.groovy.playlists

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import mx.dev.shell.android.groovy.utils.getValueForTest
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PlaylistsViewModelShould: BaseUnitTest() {

    private val repository = mock(PlaylistRepository::class.java)
    private val playlists: List<Playlist> = mock(List::class.java) as List<Playlist>
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    init {

    }

    @Test
    fun getPlaylistsFromRepository() = runBlockingTest {
        val viewModel = mockupSuccessfulCase()
        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistsFromRepository() = runBlockingTest {
        val viewModel = mockupSuccessfulCase()
        assertEquals(expected, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() {
        val viewModel = mockupFailureCase()

        assertEquals(exception, viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }

    private fun mockupFailureCase(): PlaylistsViewModel {
        runBlocking {
            `when`(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }
        val viewModel = PlaylistsViewModel(repository)
        return viewModel
    }

    private fun mockupSuccessfulCase(): PlaylistsViewModel {
        runBlocking {
            `when`(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return PlaylistsViewModel(repository)
    }
}