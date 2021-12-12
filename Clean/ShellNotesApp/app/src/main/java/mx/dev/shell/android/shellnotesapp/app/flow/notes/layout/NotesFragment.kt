package mx.dev.shell.android.shellnotesapp.app.flow.notes.layout

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mx.dev.shell.android.shellnotesapp.R
import mx.dev.shell.android.shellnotesapp.app.flow.notes.adapter.NotesAdapter
import mx.dev.shell.android.shellnotesapp.app.flow.notes.vm.NotesViewModel
import mx.dev.shell.android.shellnotesapp.app.flow.notes.vm.NotesViewModelFactory
import mx.dev.shell.android.shellnotesapp.databinding.FragmentNotesBinding
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : Fragment() {

    @Inject
    lateinit var factory: NotesViewModelFactory

    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModel: NotesViewModel
    private val notesAdapter = NotesAdapter(arrayListOf(), {
        navigateToDetail(it)
    }, {
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.deleteNote_message))
            .setPositiveButton(R.string.deleteNote_accept_btn) { dialog, _ ->
                viewModel.deleteNote(it)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.deleteNote_cancel_btn) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)

        setupView()
        setupViewModel()
        observeViewModel()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadNotes()
    }

    private fun observeViewModel() {
        viewModel.showList.observe(this as LifecycleOwner) {
            binding.notesNotesRec.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.notes.observe(this as LifecycleOwner) {
            notesAdapter.setNotes(it)
        }
        viewModel.errorMessage.observe(this as LifecycleOwner) {
            Snackbar.make(binding.notesContainer, it, Snackbar.LENGTH_LONG)
                .show()
        }
        viewModel.showEmptyMessage.observe(this as LifecycleOwner) {
            binding.notesNoItemsTxt.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.deleteSuccess.observe(this as LifecycleOwner) {
            if (it) {
                Snackbar.make(
                    binding.notesContainer,
                    getString(R.string.note_deleteSuccess_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory)
            .get(NotesViewModel::class.java)
    }

    private fun setupView() {
        activity?.title = getString(R.string.app_name)

        binding.apply {
            notesNotesRec.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = notesAdapter
                setHasFixedSize(true)
            }

            notesNewNoteBtn.setOnClickListener {
                navigateToDetail(0)
            }
        }
    }

    private fun navigateToDetail(id: Long = 0) {
        val action = NotesFragmentDirections.actionNotesFragmentToNoteFragment(id)
        findNavController().navigate(action)
    }
}