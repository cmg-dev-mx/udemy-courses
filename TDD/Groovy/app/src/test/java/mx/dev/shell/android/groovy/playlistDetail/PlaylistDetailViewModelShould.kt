package mx.dev.shell.android.groovy.playlistDetail

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import mx.dev.shell.android.groovy.utils.captureValues
import mx.dev.shell.android.groovy.utils.getValueForTest
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class PlaylistDetailViewModelShould: BaseUnitTest() {

    private val id = "1"
    private val service = mock(PlaylistDetailService::class.java)
    private val playlistDetail = mock(PlaylistDetail::class.java)
    private val expected = Result.success(playlistDetail)
    private val exception = RuntimeException("Something went wrong")

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

    @Test
    fun emitsErrorWhenServiceFails() = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.getPlaylistDetail(id)

        assertEquals(exception, viewModel.playlistDetail.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showLoaderWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetail(id)
            viewModel.playlistDetail.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderAfterPlaylistDetailDoad() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetail(id)
            viewModel.playlistDetail.getValueForTest()

            assertEquals(false, values.last())
        }
    }


    private suspend fun mockFailureCase(): PlaylistDetailViewModel {
        `when`(service.fetchPlaylistDetail(id)).thenReturn(
            flow { emit(Result.failure<PlaylistDetail>(exception)) }
        )

        return PlaylistDetailViewModel(service)
    }

    private suspend fun mockSuccessfulCase(): PlaylistDetailViewModel {
        `when`(service.fetchPlaylistDetail(id)).thenReturn(
            flow { emit(expected) }
        )
        return PlaylistDetailViewModel(service)
    }
}