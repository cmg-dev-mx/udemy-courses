package mx.dev.shell.android.shellnotesapp.app.flow.notes.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.dev.shell.android.shellnotesapp.core.usecase.NoteUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory @Inject constructor(
    private val service: NoteUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(service) as T
    }
}