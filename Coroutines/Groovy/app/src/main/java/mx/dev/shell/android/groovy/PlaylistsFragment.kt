package mx.dev.shell.android.groovy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.dev.shell.android.groovy.databinding.FragmentPlaylistsBinding

class PlaylistsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistsBinding
    private lateinit var viewModel: PlaylistsViewModel

    private val playlistsAdapter = PlaylistsAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        setupView()

        viewModel = ViewModelProvider(this).get(PlaylistsViewModel::class.java)

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null) {
                playlistsAdapter.updateList(playlists.getOrNull()!!)
            } else {
                // TODO
            }
        }
    }

    private fun setupView() {
        binding.playlistsListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playlistsAdapter
        }
    }
}