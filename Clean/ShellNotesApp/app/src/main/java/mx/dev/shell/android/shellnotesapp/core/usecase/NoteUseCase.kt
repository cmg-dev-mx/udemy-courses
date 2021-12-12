package mx.dev.shell.android.shellnotesapp.core.usecase

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo

interface NoteUseCase {
    suspend fun saveNewNote(note: NoteBo): Flow<Result<NoteBo>>
    suspend fun loadNote(id: Long): Flow<Result<NoteBo>>
    suspend fun deleteNote(id: Long): Flow<Result<Boolean>>
}
