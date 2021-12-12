package mx.dev.shell.android.shellnotesapp.db.source

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.shellnotesapp.db.dao.NoteDao
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import mx.dev.shell.android.shellnotesapp.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class NoteDataSourceShould : BaseUnitTest() {

    private val noteDao = mock(NoteDao::class.java)
    private val notes: List<NoteDo> = mock(List::class.java) as List<NoteDo>
    private val exception = RuntimeException("Something went wrong")
    private val exceptionNotFound = RuntimeException("Note not found")
    private val noteDo = NoteDo("title", "content", 1000L)
    private val noteDoSaved = NoteDo("title", "content", 1000L, 1L)
    private val id = 1L

    @Test
    fun getNotesFromDao() = runBlockingTest {
        val source = mockSuccessfulCase()
        source.getNotes()
        verify(noteDao, times(1)).getAllNotes()
    }

    @Test
    fun emitNotesFromDao() = runBlockingTest {
        val source = mockSuccessfulCase()
        assertEquals(notes, source.getNotes().first().getOrNull())
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val source = mockFailureCase()
        assertEquals(exception, source.getNotes().first().exceptionOrNull())
    }

    @Test
    fun saveNote() = runBlockingTest {
        `when`(noteDao.saveNote(noteDo)).thenReturn(1L)
        val source = mockSuccessfulCase()
        assertEquals(1L, source.saveNote(noteDo).first().getOrNull()!!.id)
    }

    @Test
    fun emitErrorWhenSaveErrorFails() = runBlockingTest {
        `when`(noteDao.saveNote(noteDo)).thenThrow(exception)
        val source = mockFailureCase()
        assertEquals(exception, source.saveNote(noteDo).first().exceptionOrNull()!!)
    }

    @Test
    fun getNoteFromDao() = runBlockingTest {
        val source = mockSuccessfulCase()
        source.getNote(id)
        verify(noteDao, times(1)).getNoteById(id)
    }

    @Test
    fun emitNoteFromDao() = runBlockingTest {
        val source = mockSuccessfulCase()
        assertEquals(noteDoSaved, source.getNote(id).first().getOrNull())
    }

    @Test
    fun emitErrorWhenLoadNoteFails() = runBlockingTest {
        val source = mockFailureCase()
        assertEquals(exception, source.getNote(id).first().exceptionOrNull())
    }

    @Test
    fun deleteNoteFromDao() = runBlockingTest {
        val source = mockSuccessfulCase()
        source.deleteNote(id)
        verify(noteDao, times(1)).deleteNote(id)
    }

    @Test
    fun deleteNote() = runBlockingTest {
        val source = mockSuccessfulCase()
        assertEquals(true, source.deleteNote(id).first().getOrNull()!!)
    }

    @Test
    fun emitErrorWhenReceiveErrorFromDao() = runBlockingTest {
        val source = mockFailureCase()
        assertEquals(exception, source.deleteNote(id).first().exceptionOrNull()!!)
    }

    @Test
    fun emitErrorWhenNoteNotFound() = runBlockingTest {
        `when`(noteDao.deleteNote(id)).thenReturn(0)
        val source = NoteDataSourceImpl(noteDao)
        assertEquals(
            exceptionNotFound.message,
            source.deleteNote(id).first().exceptionOrNull()!!.message
        )
    }

    private suspend fun mockFailureCase(): NoteDataSourceImpl {
        `when`(noteDao.getAllNotes()).thenThrow(exception)
        `when`(noteDao.getNoteById(id)).thenThrow(exception)
        `when`(noteDao.deleteNote(id)).thenThrow(exception)
        return NoteDataSourceImpl(noteDao)
    }

    private suspend fun mockSuccessfulCase(): NoteDataSourceImpl {
        `when`(noteDao.getAllNotes()).thenReturn(notes)
        `when`(noteDao.getNoteById(id)).thenReturn(noteDoSaved)
        `when`(noteDao.deleteNote(id)).thenReturn(1)
        return NoteDataSourceImpl(noteDao)
    }
}