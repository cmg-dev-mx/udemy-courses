package mx.dev.shell.android.groovy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import mx.dev.shell.android.groovy.utils.MainCoroutineScopeRule
import mx.dev.shell.android.groovy.utils.getValueForTest
import org.junit.Test

import org.junit.Rule
import org.mockito.Mockito.*

class PlaylistsViewModelShould {

    @get:Rule
    var coroutineScopeRule = MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel: PlaylistsViewModel
    private val repository = mock(PlaylistRepository::class.java)

    init {
        viewModel = PlaylistsViewModel(repository)
    }

    @Test
    fun getPlaylistsFromRepository() {
        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }
}