package mx.dev.shell.android.shellnotesapp.repository.source

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo

interface NoteDataSource {
    suspend fun getNotes(): Flow<Result<List<NoteDo>>>
    suspend fun saveNote(noteDo: NoteDo): Flow<Result<NoteDo>>
    suspend fun getNote(id: Long): Flow<Result<NoteDo>>
    suspend fun deleteNote(id: Long): Flow<Result<Boolean>>
}