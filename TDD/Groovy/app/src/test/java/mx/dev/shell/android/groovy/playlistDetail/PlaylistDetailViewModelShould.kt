package mx.dev.shell.android.groovy.playlistDetail

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.groovy.utils.BaseUnitTest
import mx.dev.shell.android.groovy.utils.getValueForTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class PlaylistDetailViewModelShould: BaseUnitTest() {

    private val id = "1"
    private val service = mock(PlaylistDetailService::class.java)

    @Test
    fun getPlaylistDetailFromService() = runBlockingTest {
        val viewModel = PlaylistDetailViewModel(service)
        viewModel.getPlaylistDetail(id)
        viewModel.playlistDetail.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetail(id)

    }
}