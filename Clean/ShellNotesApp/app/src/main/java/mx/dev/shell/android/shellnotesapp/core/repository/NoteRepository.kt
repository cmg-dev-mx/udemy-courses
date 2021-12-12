package mx.dev.shell.android.shellnotesapp.core.repository

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo

interface NoteRepository {
    suspend fun queryNotes(): Flow<Result<List<NoteBo>>>
    suspend fun saveNote(note: NoteBo): Flow<Result<NoteBo>>
    suspend fun loadNote(id: Long): Flow<Result<NoteBo>>
    suspend fun deleteNote(id: Long): Flow<Result<Boolean>>
}
