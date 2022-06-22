package mx.dev.shell.android.mealsapp.flow.categories.vm

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mx.dev.shell.android.mealsapp.core.model.CategoryBo
import mx.dev.shell.android.mealsapp.repository.category.CategoryRepository
import mx.dev.shell.android.mealsapp.utils.BaseUnitTest
import mx.dev.shell.android.mealsapp.utils.captureValues
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
internal class CategoriesViewModelShould : BaseUnitTest() {

    private val repository = mock(CategoryRepository::class.java)
    private val expected = mock(List::class.java) as List<CategoryBo>
    private val expectedException = RuntimeException("Something went wrong")

    @Test
    fun getCategoriesFromRepository() = runTest {
        val vm = mockSuccessfulCase()
        vm.getNotes()
        verify(repository, times(1)).getNotes()
    }

    @Test
    fun emitCategoriesFromRepository() = runTest {
        val vm = mockSuccessfulCase()
        vm.categories.captureValues {
            vm.getNotes()
            assertEquals(expected, values.first())
        }
    }

    @Test
    fun emitErrorFromRepository() = runTest {
        val vm = mockFailureCase()
        vm.errorMessage.captureValues {
            vm.getNotes()
            assertEquals(expectedException.message, values.first())
        }
    }

    @Test
    fun showProgressbarWhenGetNotes() = runTest {
        val vm = mockSuccessfulCase()
        vm.showProgressbar.captureValues {
            vm.getNotes()
            assertEquals(true, values.first())
        }
    }

    @Test
    fun hideProgressbarWhenGetNotes() = runTest {
        val vm = mockSuccessfulCase()
        vm.showProgressbar.captureValues {
            vm.getNotes()
            assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessfulCase(): CategoriesViewModel {
        `when`(repository.getNotes()).thenReturn(flow {
            emit(Result.success(expected))
        })
        return CategoriesViewModel(repository)
    }

    private suspend fun mockFailureCase(): CategoriesViewModel {
        `when`(repository.getNotes()).thenReturn(flow {
            emit(Result.failure(expectedException))
        })
        return CategoriesViewModel(repository)
    }
}