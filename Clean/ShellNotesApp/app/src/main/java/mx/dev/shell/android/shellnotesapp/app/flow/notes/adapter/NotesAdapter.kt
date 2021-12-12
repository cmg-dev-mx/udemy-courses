package mx.dev.shell.android.shellnotesapp.app.flow.notes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.dev.shell.android.shellnotesapp.R
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.databinding.ItemNoteBinding

class NotesAdapter(
    private val notes: ArrayList<NoteBo>,
    private val itemSelectedListener: (Long) -> Unit,
    private val itemLongSelectedListener: (Long) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) =
        holder.bind(
            notes[position],
            notes.lastIndex == position,
            itemSelectedListener,
            itemLongSelectedListener
        )

    override fun getItemCount() = notes.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(newNotes: List<NoteBo>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    class NotesViewHolder(
        private val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            note: NoteBo,
            isLast: Boolean,
            itemSelected: (Long) -> Unit,
            itemLongSelectedListener: (Long) -> Unit
        ) {
            binding.apply {
                itemNoteTitle.text = note.title
                itemNoteContent.text = note.content

                val lastUpdateMessage = binding.root.context.getString(
                    R.string.item_note_lastUpdateMessage,
                    note.lastUpdateFormatted()
                )
                itemNoteUpdatedBy.text = lastUpdateMessage

                itemNoteLastWhiteSpace.visibility = if (isLast) View.VISIBLE else View.GONE

                itemNoteContainer.setOnClickListener {
                    itemSelected(note.id)
                }

                itemNoteContainer.setOnLongClickListener {
                    itemLongSelectedListener(note.id)
                    true
                }
            }
        }
    }
}