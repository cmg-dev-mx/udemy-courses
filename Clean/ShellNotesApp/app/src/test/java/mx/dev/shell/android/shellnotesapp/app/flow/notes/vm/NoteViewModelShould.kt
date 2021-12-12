package mx.dev.shell.android.shellnotesapp.app.flow.notes.vm

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.core.usecase.NoteUseCase
import mx.dev.shell.android.shellnotesapp.utils.BaseUnitTest
import mx.dev.shell.android.shellnotesapp.utils.captureValues
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NoteViewModelShould : BaseUnitTest() {

    private val service = mock(NoteUseCase::class.java)
    private val note = mock(NoteBo::class.java)
    private val exception = RuntimeException("Something went wrong")
    private val id = 1L
    private val savedNote = NoteBo(id, "Title", "Content", 1000L)

    @Test
    fun saveNoteFromUseCase() = runBlockingTest {
        val viewModel = NoteViewModel(service)
        viewModel.saveNote(note)
        verify(service, times(1)).saveNewNote(note)
    }

    @Test
    fun emitSuccessfulWhenSaveNewNote() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.success.captureValues {
            viewModel.saveNote(note)
            assertEquals(true, values.last())
        }
    }

    @Test
    fun emitErrorWhenReceiveError() = runBlockingTest {
        val viewModel = mockFailureCase()

        viewModel.errorMessage.captureValues {
            viewModel.saveNote(note)
            assertEquals(exception.message, values.last())
        }
    }

    @Test
    fun showLoaderWhileSaving() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.saveNote(note)
            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderAfterSaved() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.saveNote(note)
            assertEquals(false, values.last())
        }
    }

    @Test
    fun loadNoteFromUseCase() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loadNote(id)
        verify(service, times(1)).loadNote(id)
    }

    @Test
    fun emitNoteWhenLoadNote() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.savedNote.captureValues {
            viewModel.loadNote(id)
            assertEquals(savedNote, values[0])
        }
    }

    @Test
    fun emitErrorWhenReceiveErrorInLoading() = runBlockingTest {
        val viewModel = mockFailureCase()

        viewModel.errorMessage.captureValues {
            viewModel.loadNote(id)
            assertEquals(exception.message, values.last())
        }
    }

    @Test
    fun showLoaderWhileLoadingSavedNote() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.loadNote(id)
            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderAfterLoadingSavedNote() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.loadNote(id)
            assertEquals(false, values.last())
        }
    }

    @Test
    fun deleteNoteFromUseCase() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.deleteNote(id)
        verify(service, times(1)).deleteNote(id)
    }

    @Test
    fun emitSuccessfulWhenDeleteNote() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.deleteSuccess.captureValues {
            viewModel.deleteNote(id)
            assertEquals(true, values.last())
        }
    }

    @Test
    fun emitErrorWhenReceiveErrorDeletingNote() = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.errorMessage.captureValues {
            viewModel.deleteNote(id)
            assertEquals(exception.message, values.last())
        }
    }

    @Test
    fun showLoaderBeforeDeletingNote() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.deleteNote(id)
            assertEquals(true, values.first())
        }
    }

    @Test
    fun hideLoaderAfterDeleteNote() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.deleteNote(id)
            assertEquals(false, values.last())
        }
    }

    private suspend fun mockFailureCase(): NoteViewModel {
        `when`(service.saveNewNote(note)).thenReturn(
            flow {
                emit(Result.failure<NoteBo>(exception))
            }
        )

        `when`(service.loadNote(id)).thenReturn(
            flow {
                emit(Result.failure<NoteBo>(exception))
            }
        )

        `when`(service.deleteNote(id)).thenReturn(
            flow {
                emit(Result.failure<Boolean>(exception))
            }
        )

        return NoteViewModel(service)
    }

    private suspend fun mockSuccessfulCase(): NoteViewModel {
        `when`(service.saveNewNote(note)).thenReturn(
            flow {
                emit(Result.success(note))
            }
        )

        `when`(service.loadNote(id)).thenReturn(
            flow {
                emit(Result.success(savedNote))
            }
        )

        `when`(service.deleteNote(id)).thenReturn(
            flow {
                emit(Result.success(true))
            }
        )

        return NoteViewModel(service)
    }
}