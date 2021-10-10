package mx.dev.shell.android.groovy.playlistDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mx.dev.shell.android.groovy.R
import mx.dev.shell.android.groovy.databinding.FragmentPlaylistDetailBinding
import mx.dev.shell.android.groovy.databinding.FragmentPlaylistsBinding
import mx.dev.shell.android.groovy.playlists.Playlist
import mx.dev.shell.android.groovy.playlists.PlaylistsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private val args: PlaylistDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentPlaylistDetailBinding
    private lateinit var viewModel: PlaylistDetailViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)

        val id = args.playlistId

        setupViewModel()

        viewModel.getPlaylistDetail(id)

        observePlaylistDetail()
        observeLoader()

        return binding.root
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> binding.playlistDetailLoader.visibility = View.VISIBLE
                else -> binding.playlistDetailLoader.visibility = View.GONE
            }
        }
    }

    private fun observePlaylistDetail() {
        viewModel.playlistDetail.observe(this as LifecycleOwner) { playlist ->
            if (playlist.getOrNull() != null) {
                setupContent(playlist.getOrNull()!!)
            } else {
                Snackbar.make(binding.playlistContainer, R.string.generic_error, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setupContent(playlist: PlaylistDetail) {
        binding.apply {
            playlistDetailName.text = playlist.name
            playlistDetailDetail.text = playlist.details
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PlaylistDetailViewModel::class.java)
    }
}