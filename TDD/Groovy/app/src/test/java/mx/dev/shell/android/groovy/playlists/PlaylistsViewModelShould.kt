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

@ExperimentalCoroutinesApi
class PlaylistsViewModelShould: BaseUnitTest() {

    private val repository = mock(PlaylistRepository::class.java)
    private val playlists: List<Playlist> = mock(List::class.java) as List<Playlist>
    private val expected = Result.success(playlists)

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