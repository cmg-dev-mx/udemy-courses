package mx.dev.shell.android.shellnotesapp.db.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mx.dev.shell.android.shellnotesapp.db.dao.NoteDao
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import mx.dev.shell.android.shellnotesapp.repository.source.NoteDataSource
import javax.inject.Inject

class NoteDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteDataSource {

    override suspend fun getNotes() = try {
        val notes = noteDao.getAllNotes()
        flow { emit(Result.success(notes)) }
    } catch (e: Exception) {
        flow { emit(Result.failure<List<NoteDo>>(e)) }
    }

    override suspend fun saveNote(noteDo: NoteDo): Flow<Result<NoteDo>> {
        return try {
            val id = noteDao.saveNote(noteDo)
            noteDo.id = id
            flow { emit(Result.success(noteDo)) }
        } catch (e: Exception) {
            flow { emit(Result.failure<NoteDo>(e)) }
        }
    }

    override suspend fun getNote(id: Long): Flow<Result<NoteDo>> {
        return try {
            val note = noteDao.getNoteById(id)
            flow { emit(Result.success(note)) }
        } catch (e: Exception) {
            flow { emit(Result.failure<NoteDo>(e)) }
        }
    }

    override suspend fun deleteNote(id: Long): Flow<Result<Boolean>> {
        return try {
            val rowsAffected = noteDao.deleteNote(id)
            if (rowsAffected > 0) {
                flow { emit(Result.success(true)) }
            } else {
                flow { emit(Result.failure<Boolean>(RuntimeException("Note not found"))) }
            }
        } catch (e: Exception) {
            flow { emit((Result.failure<Boolean>(e))) }
        }
    }
}
