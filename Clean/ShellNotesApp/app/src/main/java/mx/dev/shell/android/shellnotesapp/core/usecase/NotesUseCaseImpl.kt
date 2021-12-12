package mx.dev.shell.android.shellnotesapp.core.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mx.dev.shell.android.shellnotesapp.core.repository.NoteRepository
import javax.inject.Inject

class NotesUseCaseImpl @Inject constructor(
    private val repository: NoteRepository
) : NotesUseCase {

    override suspend fun queryNotes() = repository.queryNotes()
        .map {
            if (it.isSuccess) {
                Result.success(
                    it.getOrNull()!!
                        .sortedByDescending { noteBo ->
                            noteBo.lastUpdate
                        }
                )
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }

    override suspend fun deleteNote(id: Long): Flow<Result<Boolean>> {
        return repository.deleteNote(id)
            .map {
                if (it.isSuccess) {
                    Result.success(it.getOrNull()!!)
                } else {
                    Result.failure(it.exceptionOrNull()!!)
                }
            }
    }
}