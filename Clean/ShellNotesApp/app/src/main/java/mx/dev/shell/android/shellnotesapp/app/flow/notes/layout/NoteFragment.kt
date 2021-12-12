package mx.dev.shell.android.shellnotesapp.app.flow.notes.layout

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mx.dev.shell.android.shellnotesapp.R
import mx.dev.shell.android.shellnotesapp.app.flow.notes.vm.NoteViewModel
import mx.dev.shell.android.shellnotesapp.app.flow.notes.vm.NoteViewModelFactory
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.databinding.FragmentNoteBinding
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : Fragment() {

    @Inject
    lateinit var factory: NoteViewModelFactory

    private val args: NoteFragmentArgs by navArgs()

    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel

    private var id: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)


        setupView()
        setupViewModel()
        observeViewModel()

        id = args.noteId
        if (id != 0L) {
            viewModel.loadNote(id)
        } else {
            binding.noteDeleteBtn.visibility = View.GONE
        }

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.success.observe(this as LifecycleOwner) {
            if (it) {
                Snackbar.make(
                    binding.noteContainer,
                    getString(R.string.note_saveSuccess_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                goToNotesScreen()
            }
        }
        viewModel.errorMessage.observe(this as LifecycleOwner) {
            if (it.isNotEmpty()) {
                Snackbar.make(
                    binding.noteContainer,
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        viewModel.loader.observe(this as LifecycleOwner) {
            binding.noteProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.savedNote.observe(this as LifecycleOwner) {
            activity?.title = getString(R.string.note_title_edit)
            binding.apply {
                noteTitleEdt.setText(it.title)
                noteContentEdt.setText(it.content)
                noteDeleteBtn.visibility = View.VISIBLE
            }
        }
        viewModel.deleteSuccess.observe(this as LifecycleOwner) {
            if (it) {
                Snackbar.make(
                    binding.noteContainer,
                    getString(R.string.note_deleteSuccess_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                goToNotesScreen()
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory)
            .get(NoteViewModel::class.java)
    }

    private fun setupView() {
        activity?.title = getString(R.string.note_title)

        binding.apply {
            noteSaveBtn.setOnClickListener {
                val noteBo = NoteBo(
                    id = id,
                    title = noteTitleEdt.text.toString(),
                    content = noteContentEdt.text.toString(),
                    lastUpdate = System.currentTimeMillis()
                )
                viewModel.saveNote(noteBo)
            }
            noteCancelBtn.setOnClickListener {
                goToNotesScreen()
            }
            noteDeleteBtn.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.deleteNote_message))
                    .setPositiveButton(R.string.deleteNote_accept_btn) { dialog, _ ->
                        viewModel.deleteNote(id)
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.deleteNote_cancel_btn) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun goToNotesScreen() {
        activity?.onBackPressed()
    }
}
