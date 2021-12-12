package mx.dev.shell.android.shellnotesapp.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.core.repository.NoteRepository
import javax.inject.Inject

class NoteUseCaseImpl @Inject constructor(private val repository: NoteRepository) : NoteUseCase {

    override suspend fun saveNewNote(note: NoteBo): Flow<Result<NoteBo>> {
        return if (note.title.isBlank() || note.content.isBlank()) {
            flow { emit(Result.failure<NoteBo>(RuntimeException("Incomplete data"))) }
        } else {
            repository.saveNote(note)
                .map {
                    if (it.isSuccess && it.getOrNull()!!.id != 0L) {
                        Result.success(it.getOrNull()!!)
                    } else {
                        Result.failure(RuntimeException("Error when saving new note"))
                    }
                }
        }
    }

    override suspend fun loadNote(id: Long): Flow<Result<NoteBo>> {
        return repository.loadNote(id)
            .map {
                if (it.isSuccess) {
                    Result.success(it.getOrNull()!!)
                } else {
                    Result.failure(RuntimeException("Error when loading note"))
                }
            }
    }

    override suspend fun deleteNote(id: Long): Flow<Result<Boolean>> {
        return repository.deleteNote(id)
            .map {
                if (it.isSuccess) {
                    Result.success(it.getOrNull()!!)
                } else {
                    Result.failure(RuntimeException("Error when deleting note"))
                }
            }
    }
}