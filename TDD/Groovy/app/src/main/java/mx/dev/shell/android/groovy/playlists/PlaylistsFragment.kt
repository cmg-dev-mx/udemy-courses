package mx.dev.shell.android.groovy.playlists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.dev.shell.android.groovy.databinding.FragmentPlaylistsBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaylistsFragment : Fragment() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.7:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(PlaylistApi::class.java)

    private val service  = PlaylistService(api)
    private lateinit var viewModelFactory: PlaylistsViewModelFactory
    private val repository = PlaylistRepository(service)

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
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistsViewModelFactory(repository)
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