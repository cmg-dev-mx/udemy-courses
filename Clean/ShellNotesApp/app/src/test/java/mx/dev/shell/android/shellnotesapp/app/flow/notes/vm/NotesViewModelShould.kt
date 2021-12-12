package mx.dev.shell.android.shellnotesapp.app.flow.notes.vm

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.core.usecase.NotesUseCase
import mx.dev.shell.android.shellnotesapp.utils.BaseUnitTest
import mx.dev.shell.android.shellnotesapp.utils.captureValues
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class NotesViewModelShould : BaseUnitTest() {

    private val notesUseCase = mock(NotesUseCase::class.java)
    private val notes: List<NoteBo> = mock(List::class.java) as List<NoteBo>
    private val expected = Result.success(notes)
    private val exception = RuntimeException("Something went wrong.")
    private val id = 1L

    @Test
    fun queryNotesFromUseCase() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loadNotes()
        verify(notesUseCase, times(1)).queryNotes()
    }

    @Test
    fun emmitNotesFromUseCase() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.notes.captureValues {
            viewModel.loadNotes()
            assertEquals(notes, values.first())
        }
    }

    @Test
    fun showEmptyMessageWhenReceiveEmptyList() = runBlockingTest {
        val viewModel = mockSuccessfulEmptyCase()

        viewModel.showEmptyMessage.captureValues {
            viewModel.loadNotes()
            assertEquals(true, values.last())
        }
    }

    @Test
    fun hideNoteListWhenReceiveEmptyList() = runBlockingTest {
        val viewModel = mockSuccessfulEmptyCase()

        viewModel.showList.captureValues {
            viewModel.loadNotes()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun hideEmptyMessageWhenReceiveNonEmptyList() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.showEmptyMessage.captureValues {
            viewModel.loadNotes()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun emmitErrorWhenReceiveErrorFromUseCase() = runBlockingTest {
        val viewModel = mockFailureCase()
        viewModel.errorMessage.captureValues {
            viewModel.loadNotes()
            assertEquals(exception.message, values.last())
        }
    }

    @Test
    fun showErrorMessageWhenReceiveError() = runBlockingTest {
        val viewModel = mockFailureCase()

        viewModel.errorMessage.captureValues {
            viewModel.loadNotes()
            assertEquals("Something went wrong.", values.last())
        }
    }

    @Test
    fun showLoaderWhenLoadNotes() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.loadNotes()
            assertEquals(true, values.first())
        }
    }

    @Test
    fun hideLoaderWhenLoadNotes() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.loadNotes()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun deleteNoteFromUseCase() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.deleteNote(id)
        verify(notesUseCase, times(1)).deleteNote(id)
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
    fun loadNotesAfterDeleteNote() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        val otherViewModel = spy(viewModel)
        otherViewModel.deleteNote(id)
        verify(otherViewModel, times(1)).loadNotes()
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

    private suspend fun mockFailureCase(): NotesViewModel {
        `when`(notesUseCase.queryNotes()).thenReturn(
            flow {
                emit(Result.failure<List<NoteBo>>(exception))
            }
        )

        `when`(notesUseCase.deleteNote(id)).thenReturn(
            flow {
                emit(Result.failure<Boolean>(exception))
            }
        )

        return NotesViewModel(notesUseCase)
    }

    private suspend fun mockSuccessfulCase(): NotesViewModel {
        `when`(notesUseCase.queryNotes()).thenReturn(
            flow {
                emit(expected)
            }
        )

        `when`(notesUseCase.deleteNote(id)).thenReturn(
            flow {
                emit(Result.success(true))
            }
        )

        return NotesViewModel(notesUseCase)
    }

    private suspend fun mockSuccessfulEmptyCase(): NotesViewModel {
        `when`(notesUseCase.queryNotes()).thenReturn(
            flow {
                emit(Result.success(listOf<NoteBo>()))
            }
        )

        return NotesViewModel(notesUseCase)
    }
}