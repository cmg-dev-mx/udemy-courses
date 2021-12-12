package mx.dev.shell.android.shellnotesapp.core.usecase

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.core.repository.NoteRepository
import mx.dev.shell.android.shellnotesapp.utils.BaseUnitTest
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class NotesUseCaseShould : BaseUnitTest() {

    private val repository = mock(NoteRepository::class.java)
    private val exception = RuntimeException("Something went wrong")
    private val id = 1L

    @Test
    fun queryNotesFromRepository() = runBlockingTest {
        val useCase = NotesUseCaseImpl(repository)
        useCase.queryNotes()
        verify(repository, times(1)).queryNotes()
    }

    @Test
    fun emitReversedSortedNotesByLastUpdateFromRepository() = runBlockingTest {
        val unsortedNotes = listOf(
            NoteBo(1, "title", "content", 1633827600),
            NoteBo(2, "title", "content", 1633834800),
            NoteBo(3, "title", "content", 1633842000),
            NoteBo(4, "title", "content", 1633838400),
            NoteBo(5, "title", "content", 1633831200)
        )

        val sortedNotes = listOf(
            NoteBo(3, "title", "content", 1633842000),
            NoteBo(4, "title", "content", 1633838400),
            NoteBo(2, "title", "content", 1633834800),
            NoteBo(5, "title", "content", 1633831200),
            NoteBo(1, "title", "content", 1633827600)
        )

        `when`(repository.queryNotes()).thenReturn(
            flow {
                emit(Result.success(unsortedNotes))
            }
        )

        val useCase = NotesUseCaseImpl(repository)

        val notes = useCase.queryNotes()
            .first()
            .getOrNull()!!

        notes.forEachIndexed { index, noteBo ->
            assertEquals(sortedNotes[index].lastUpdate, noteBo.lastUpdate)
        }
    }

    @Test
    fun emitErrorWhenErrorReceived() = runBlockingTest {
        `when`(repository.queryNotes()).thenReturn(
            flow {
                emit(Result.failure<List<NoteBo>>(exception))
            }
        )
        val useCase = NotesUseCaseImpl(repository)

        assertEquals(exception, useCase.queryNotes().singleOrNull()!!.exceptionOrNull()!!)
    }

    @Test
    fun callDeleteFromRepository() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        useCase.deleteNote(id)
        verify(repository, times(1)).deleteNote(id)
    }

    @Test
    fun deleteNote() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        assertEquals(true, useCase.deleteNote(id).first().getOrNull())
    }

    @Test
    fun emitErrorWhenReceiveErrorDeletingNote() = runBlockingTest {
        val useCase = mockFailureCase()
        assertEquals(exception, useCase.deleteNote(id).first().exceptionOrNull())
    }

    private suspend fun mockFailureCase(): NotesUseCaseImpl {
        `when`(repository.deleteNote(id)).thenReturn(
            flow {
                emit(Result.failure<Boolean>(exception))
            }
        )
        return NotesUseCaseImpl(repository)
    }

    private suspend fun mockSuccessfulCase(): NoteUseCaseImpl {
        `when`(repository.deleteNote(id)).thenReturn(
            flow {
                emit(Result.success(true))
            }
        )
        return NoteUseCaseImpl(repository)
    }
}