package mx.dev.shell.android.shellnotesapp.core.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo

interface NotesUseCase {
    suspend fun queryNotes(): Flow<Result<List<NoteBo>>>
    suspend fun deleteNote(id: Long): Flow<Result<Boolean>>
}
