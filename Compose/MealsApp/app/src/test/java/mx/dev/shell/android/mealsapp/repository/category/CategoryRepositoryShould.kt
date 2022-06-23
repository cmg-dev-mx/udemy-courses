package mx.dev.shell.android.mealsapp.repository.category

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import mx.dev.shell.android.mealsapp.core.model.CategoryBo
import mx.dev.shell.android.mealsapp.repository.category.map.CategoryMap
import mx.dev.shell.android.mealsapp.utils.BaseUnitTest
import mx.dev.shell.android.mealsapp.web.model.categories.Category
import mx.dev.shell.android.mealsapp.web.service.MealService
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class CategoryRepositoryShould : BaseUnitTest() {

    private val service = mock(MealService::class.java)
    private val map = mock(CategoryMap::class.java)

    private val serviceResponse = mock(List::class.java) as List<Category>
    private val expected = mock(List::class.java) as List<CategoryBo>
    private val expectedException = RuntimeException("Something went wrong")

    @Test
    fun getCategoriesFromService() = runTest {
        val repository = CategoryRepositoryImpl(service, map)
        repository.getNotes()
        verify(service, times(1)).getCategories()
    }

    @Test
    fun emitCategoriesFromService() = runTest {
        val repository = mockSuccessfulCase()
        assertEquals(expected, repository.getNotes().first().getOrNull())
    }

    @Test
    fun emitErrorFromService() = runTest {
        `when`(service.getCategories()).thenReturn(flow {
            emit(Result.failure(expectedException))
        })
        val repository = CategoryRepositoryImpl(service, map)
        assertEquals(expectedException, repository.getNotes().first().exceptionOrNull())
    }

    private suspend fun mockSuccessfulCase(): CategoryRepositoryImpl {
        `when`(service.getCategories()).thenReturn(flow {
            emit(Result.success(serviceResponse))
        })
        `when`(map.fromServiceListToBo(serviceResponse)).thenReturn(expected)
        return CategoryRepositoryImpl(service, map)
    }
}