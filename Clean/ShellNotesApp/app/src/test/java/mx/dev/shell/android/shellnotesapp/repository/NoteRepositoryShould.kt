package mx.dev.shell.android.shellnotesapp.repository

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import mx.dev.shell.android.shellnotesapp.repository.implementation.NoteRepositoryImpl
import mx.dev.shell.android.shellnotesapp.repository.mapper.NoteMapper
import mx.dev.shell.android.shellnotesapp.repository.mapper.NotesMapper
import mx.dev.shell.android.shellnotesapp.repository.source.NoteDataSource
import mx.dev.shell.android.shellnotesapp.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class NoteRepositoryShould : BaseUnitTest() {

    private val dataSource = mock(NoteDataSource::class.java)
    private val notesMapper = mock(NotesMapper::class.java)

    private val notesDo: List<NoteDo> = mock(List::class.java) as List<NoteDo>
    private val notes: List<NoteBo> = mock(List::class.java) as List<NoteBo>
    private val exception = RuntimeException("Something went wrong")

    private val noteMapper = mock(NoteMapper::class.java)
    private val noteBo = NoteBo(0L, "title", "content", 1000L)
    private val noteDo = NoteDo("title", "content", 1000L)
    private val noteDoSaved = NoteDo("title", "content", 1000L, 1L)
    private val noteBoSaved = NoteBo(1L, "title", "content", 1000L)

    private val id = 1L

    @Test
    fun getNotesFromDatabase() = runBlockingTest {
        val repository = mockSuccessCase()
        repository.queryNotes()
        verify(dataSource, times(1)).getNotes()
    }

    @Test
    fun emitMappedNotesFromDatabase() = runBlockingTest {
        val repository = mockSuccessCase()
        assertEquals(notes, repository.queryNotes().first().getOrNull())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.queryNotes().first().exceptionOrNull())
    }

    @Test
    fun emitErrorWhenMapperEmitError() = runBlockingTest {
        val repository = mockFailureMapperCase()
        `when`(notesMapper.invoke(notesDo)).thenThrow(exception)
        assertEquals(exception, repository.queryNotes().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessCase()
        repository.queryNotes().first()
        verify(notesMapper, times(1)).invoke(notesDo)
    }

    @Test
    fun saveNote() = runBlockingTest {
        val repository = mockSuccessCase()
        assertEquals(noteBoSaved, repository.saveNote(noteBo).first().getOrNull()!!)
    }

    @Test
    fun emitErrorWhenSaveNoteEmitError() = runBlockingTest {
        `when`(noteMapper.fromBoToDo(noteBo)).thenReturn(noteDo)
        val repository = mockFailureCase()
        assertEquals(exception, repository.saveNote(noteBo).first().exceptionOrNull())
    }

    @Test
    fun emitErrorWhenMapperEmitErrorInBoToDo() = runBlockingTest {
        `when`(noteMapper.fromBoToDo(noteBo)).thenThrow(exception)
        val repository = mockFailureCase()
        assertEquals(exception, repository.saveNote(noteBo).first().exceptionOrNull())
    }

    @Test
    fun emitErrorWhenMapperEmitErrorInDoToBo() = runBlockingTest {
        `when`(noteMapper.fromBoToDo(noteBo)).thenReturn(noteDo)
        `when`(noteMapper.fromDoToBo(noteDoSaved)).thenThrow(exception)
        val repository = mockFailureCase()
        assertEquals(exception, repository.saveNote(noteBo).first().exceptionOrNull())
    }

    @Test
    fun getNoteFromDatabase() = runBlockingTest {
        val repository = mockSuccessCase()
        repository.loadNote(id)
        verify(dataSource, times(1)).getNote(id)
    }

    @Test
    fun emitMappedNoteFromDatabase() = runBlockingTest {
        val repository = mockSuccessCase()
        assertEquals(noteBoSaved, repository.loadNote(id).first().getOrNull())
    }

    @Test
    fun emitErrorWhenReceiveErrorWhenLoadNote() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.loadNote(id).first().exceptionOrNull())
    }

    @Test
    fun emitErrorWhenMapperEmitErrorWhenLoading() = runBlockingTest {
        val repository = mockFailureMapperCase()
        `when`(noteMapper.fromDoToBo(noteDoSaved)).thenThrow(exception)
        assertEquals(exception, repository.loadNote(id).first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapperWhenLoadNote() = runBlockingTest {
        val repository = mockSuccessCase()
        repository.loadNote(id).first()
        verify(noteMapper, times(1)).fromDoToBo(noteDoSaved)
    }

    @Test
    fun deleteNoteFromDatabase() = TestCoroutineDispatcher().runBlockingTest {
        val repository = mockSuccessCase()
        repository.deleteNote(id)
        verify(dataSource, times(1)).deleteNote(id)
    }

    @Test
    fun deleteNote() = runBlockingTest {
        val repository = mockSuccessCase()
        assertEquals(true, repository.deleteNote(id).first().getOrNull()!!)
    }

    @Test
    fun emitErrorWhenReceiveErrorOnDeletingNote() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.deleteNote(id).first().exceptionOrNull()!!)
    }

    private suspend fun mockFailureMapperCase(): NoteRepositoryImpl {
        `when`(dataSource.getNotes()).thenReturn(
            flow {
                emit(Result.success(notesDo))
            }
        )
        `when`(dataSource.saveNote(noteDo)).thenReturn(
            flow {
                emit(Result.success(noteDoSaved))
            }
        )
        `when`(dataSource.getNote(id)).thenReturn(
            flow {
                emit(Result.success(noteDoSaved))
            }
        )

        return NoteRepositoryImpl(dataSource, notesMapper, noteMapper, coroutineScope.dispatcher)
    }

    private suspend fun mockFailureCase(): NoteRepositoryImpl {
        `when`(dataSource.getNotes()).thenReturn(
            flow {
                emit(Result.failure<List<NoteDo>>(exception))
            }
        )
        `when`(dataSource.saveNote(noteDo)).thenReturn(
            flow {
                emit(Result.failure<NoteDo>(exception))
            }
        )
        `when`(dataSource.getNote(id)).thenReturn(
            flow {
                emit(Result.failure<NoteDo>(exception))
            }
        )
        `when`(dataSource.deleteNote(id)).thenReturn(
            flow {
                emit(Result.failure<Boolean>(exception))
            }
        )

        return NoteRepositoryImpl(dataSource, notesMapper, noteMapper, coroutineScope.dispatcher)
    }

    private suspend fun mockSuccessCase(): NoteRepositoryImpl {
        `when`(dataSource.getNotes()).thenReturn(
            flow {
                emit(Result.success(notesDo))
            }
        )
        `when`(notesMapper.invoke(notesDo)).thenReturn(notes)
        `when`(dataSource.saveNote(noteDo)).thenReturn(
            flow {
                emit(Result.success(noteDoSaved))
            }
        )
        `when`(noteMapper.fromBoToDo(noteBo)).thenReturn(noteDo)
        `when`(noteMapper.fromDoToBo(noteDoSaved)).thenReturn(noteBoSaved)

        `when`(dataSource.getNote(id)).thenReturn(
            flow {
                emit(Result.success(noteDoSaved))
            }
        )
        `when`(dataSource.deleteNote(id)).thenReturn(
            flow {
                emit(Result.success(true))
            }
        )

        return NoteRepositoryImpl(dataSource, notesMapper, noteMapper, coroutineScope.dispatcher)
    }
}