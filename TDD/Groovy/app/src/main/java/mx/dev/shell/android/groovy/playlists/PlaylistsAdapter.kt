package mx.dev.shell.android.groovy.playlists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.dev.shell.android.groovy.R
import mx.dev.shell.android.groovy.databinding.ItemPlaylistBinding

class PlaylistsAdapter(
    private val playlists: ArrayList<Playlist>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<PlaylistsAdapter.PlaylistsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaylistsViewHolder(
        ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) =
        holder.bind(playlists[position])

    override fun getItemCount() = playlists.size

    fun updateList(newPlaylists: List<Playlist>) {
        playlists.clear()
        playlists.addAll(newPlaylists)
        notifyDataSetChanged()
    }

    inner class PlaylistsViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(playlist: Playlist) {
            binding.apply {
                playlistName.text = playlist.name
                playlistCategory.text = playlist.category
                playlistImage.setImageResource(playlist.image)

                playlistItemContainer.setOnClickListener { listener(playlist.id) }
            }
        }
    }
}
