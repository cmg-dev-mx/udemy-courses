package mx.dev.shell.android.shellnotesapp.core.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.core.repository.NoteRepository
import mx.dev.shell.android.shellnotesapp.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NoteUseCaseShould : BaseUnitTest() {

    private val repository = mock(NoteRepository::class.java)
    private val expected = NoteBo(1L, "title", "content", 1000L)
    private val exception = RuntimeException("Something went wrong")
    private val id = 1L

    @Test
    fun callSaveFromRepository() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        useCase.saveNewNote(expected)
        verify(repository, times(1)).saveNote(expected)
    }

    @Test
    fun saveNote() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        assertEquals(1L, useCase.saveNewNote(expected).first().getOrNull()!!.id)
    }

    @Test
    fun emitErrorWhenReceiveErrorFromRepository() = runBlockingTest {
        val useCase = mockFailureCase()
        assertEquals(
            "Error when saving new note",
            useCase.saveNewNote(expected).first().exceptionOrNull()!!.message
        )
    }

    @Test
    fun emitInsufficientErrorWhenReceiveIncompleteData() = runBlockingTest {
        val useCase = NoteUseCaseImpl(repository)
        val noteBo = NoteBo(0L, "", "", 1000L)
        assertEquals(
            "Incomplete data",
            useCase.saveNewNote(noteBo).first().exceptionOrNull()!!.message
        )
    }

    @Test
    fun callLoadFromRepository() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        useCase.loadNote(id)
        verify(repository, times(1)).loadNote(id)
    }

    @Test
    fun emitErrorWhenReceiveErrorLoadingNote() = runBlockingTest {
        val useCase = mockFailureCase()
        assertEquals(
            "Error when loading note",
            useCase.loadNote(id).first().exceptionOrNull()!!.message
        )
    }

    @Test
    fun loadNote() = runBlockingTest {
        val useCase = mockSuccessfulCase()
        assertEquals(expected, useCase.loadNote(id).first().getOrNull())
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
        assertEquals(
            "Error when deleting note",
            useCase.deleteNote(id).first().exceptionOrNull()!!.message
        )
    }

    private suspend fun mockSuccessfulCase(): NoteUseCaseImpl {
        `when`(repository.saveNote(expected)).thenReturn(
            flow {
                emit(Result.success(expected))
            }
        )

        `when`(repository.loadNote(id)).thenReturn(
            flow {
                emit(Result.success(expected))
            }
        )

        `when`(repository.deleteNote(id)).thenReturn(
            flow {
                emit(Result.success(true))
            }
        )

        return NoteUseCaseImpl(repository)
    }

    private suspend fun mockFailureCase(): NoteUseCaseImpl {
        `when`(repository.saveNote(expected)).thenReturn(
            flow {
                emit(Result.failure<NoteBo>(exception))
            }
        )

        `when`(repository.loadNote(id)).thenReturn(
            flow {
                emit(Result.failure<NoteBo>(exception))
            }
        )

        `when`(repository.deleteNote(id)).thenReturn(
            flow {
                emit(Result.failure<Boolean>(exception))
            }
        )

        return NoteUseCaseImpl(repository)
    }
}