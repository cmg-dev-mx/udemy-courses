package mx.dev.shell.android.shellnotesapp.repository.implementation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import mx.dev.shell.android.shellnotesapp.app.flow.notes.di.DispatcherModule
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.core.repository.NoteRepository
import mx.dev.shell.android.shellnotesapp.repository.mapper.NoteMapper
import mx.dev.shell.android.shellnotesapp.repository.mapper.NotesMapper
import mx.dev.shell.android.shellnotesapp.repository.source.NoteDataSource
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dataSource: NoteDataSource,
    private val notesMapper: NotesMapper,
    private val noteMapper: NoteMapper,
    @DispatcherModule.IoDispatcher private val dispatcher: CoroutineDispatcher
) : NoteRepository {

    override suspend fun queryNotes() = dataSource.getNotes()
        .map {
            if (it.isSuccess) {
                try {
                    Result.success(notesMapper.invoke(it.getOrNull()!!))
                } catch (e: Exception) {
                    Result.failure<List<NoteBo>>(e)
                }
            } else {
                Result.failure(it.exceptionOrNull()!!)
            }
        }

    override suspend fun saveNote(note: NoteBo): Flow<Result<NoteBo>> {
        return try {
            dataSource.saveNote(noteMapper.fromBoToDo(note))
                .map {
                    if (it.isSuccess) {
                        Result.success(noteMapper.fromDoToBo(it.getOrNull()!!))
                    } else {
                        Result.failure(it.exceptionOrNull()!!)
                    }
                }
        } catch (e: Exception) {
            flow { emit(Result.failure<NoteBo>(e)) }
        }
    }

    override suspend fun loadNote(id: Long): Flow<Result<NoteBo>> {
        return dataSource.getNote(id)
            .map {
                if (it.isSuccess) {
                    try {
                        Result.success(noteMapper.fromDoToBo(it.getOrNull()!!))
                    } catch (e: Exception) {
                        Result.failure<NoteBo>(e)
                    }
                } else {
                    Result.failure<NoteBo>(it.exceptionOrNull()!!)
                }
            }
    }

    override suspend fun deleteNote(id: Long): Flow<Result<Boolean>> {
        return withContext(dispatcher) {
            dataSource.deleteNote(id)
                .map {
                    if (it.isSuccess) {
                        Result.success(it.getOrNull()!!)
                    } else {
                        Result.failure<Boolean>(it.exceptionOrNull()!!)
                    }
                }
        }
    }
}