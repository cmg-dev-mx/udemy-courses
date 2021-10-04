package mx.dev.shell.android.groovy.playlists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mx.dev.shell.android.groovy.databinding.FragmentPlaylistsBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: PlaylistsViewModelFactory

    private lateinit var binding: FragmentPlaylistsBinding
    private lateinit var viewModel: PlaylistsViewModel

    private val playlistsAdapter = PlaylistsAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        setupView()
        setupViewModel()
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

        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when(loading) {
                true -> binding.playlistsLoader.visibility = View.VISIBLE
                false -> binding.playlistsLoader.visibility = View.GONE
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PlaylistsViewModel::class.java)
    }

    private fun setupView() {
        binding.playlistsListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playlistsAdapter
        }
    }
}