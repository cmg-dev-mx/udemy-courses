package mx.dev.shell.android.shellnotesapp.app.flow.notes.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.core.usecase.NoteUseCase
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val service: NoteUseCase
) : ViewModel() {

    val loader = MutableLiveData<Boolean>()
    val success = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val savedNote = MutableLiveData<NoteBo>()
    val deleteSuccess = MutableLiveData<Boolean>()

    fun saveNote(note: NoteBo) {
        viewModelScope.launch {
            loader.postValue(true)
            service.saveNewNote(note)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    if (it.isSuccess) {
                        success.postValue(true)
                    } else {
                        errorMessage.postValue(it.exceptionOrNull()!!.message)
                    }
                }
        }
    }

    fun loadNote(id: Long) {
        viewModelScope.launch {
            loader.postValue(true)
            service.loadNote(id)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    if (it.isSuccess) {
                        savedNote.postValue(it.getOrNull())
                    } else {
                        errorMessage.postValue(it.exceptionOrNull()!!.message)
                    }
                }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            loader.postValue(true)
            service.deleteNote(id)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    if (it.isSuccess) {
                        deleteSuccess.postValue(true)
                    } else {
                        errorMessage.postValue(it.exceptionOrNull()!!.message)
                    }
                }
        }
    }
}
