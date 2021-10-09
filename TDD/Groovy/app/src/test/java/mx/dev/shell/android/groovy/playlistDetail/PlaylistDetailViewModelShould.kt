package mx.dev.shell.android.groovy.playlistDetail

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import mx.dev.shell.android.groovy.utils.getValueForTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class PlaylistDetailViewModelShould: BaseUnitTest() {

    private val id = "1"
    private val service = mock(PlaylistDetailService::class.java)
    private val playlistDetail = mock(PlaylistDetail::class.java)
    private val expected = Result.success(playlistDetail)

    @Test
    fun getPlaylistDetailFromService() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getPlaylistDetail(id)
        viewModel.playlistDetail.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetail(id)
    }

    @Test
    fun emitsPlaylistDetailsFromService() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getPlaylistDetail(id)

        assertEquals(expected, viewModel.playlistDetail.getValueForTest())
    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailViewModel {
        `when`(service.fetchPlaylistDetail(id)).thenReturn(
            flow { emit(expected) }
        )
        return PlaylistDetailViewModel(service)
    }
}