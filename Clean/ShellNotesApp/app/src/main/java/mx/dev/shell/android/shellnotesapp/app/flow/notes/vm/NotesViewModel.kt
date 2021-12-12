package mx.dev.shell.android.shellnotesapp.app.flow.notes.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.core.usecase.NotesUseCase
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase
) : ViewModel() {

    val showEmptyMessage = MutableLiveData<Boolean>()
    val showList = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val notes = MutableLiveData<List<NoteBo>>()
    val deleteSuccess = MutableLiveData<Boolean>()
    val loader = MutableLiveData<Boolean>()

    fun loadNotes() {
        viewModelScope.launch {
            loader.postValue(true)
            notesUseCase.queryNotes()
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    if (it.isSuccess) {
                        val list = it.getOrNull()!!
                        notes.postValue(list)
                        if (list.isEmpty()) {
                            showEmptyMessage.postValue(true)
                            showList.postValue(false)
                        } else {
                            showEmptyMessage.postValue(false)
                            showList.postValue(true)
                        }
                    } else {
                        errorMessage.postValue(it.exceptionOrNull()!!.message)
                    }
                }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            loader.postValue(true)
            notesUseCase.deleteNote(id)
                .onEach {
                    loader.postValue(false)
                }.collect {
                    if (it.isSuccess) {
                        deleteSuccess.postValue(true)
                        loadNotes()
                    } else {
                        errorMessage.postValue(it.exceptionOrNull()!!.message)
                    }
                }
        }
    }
}