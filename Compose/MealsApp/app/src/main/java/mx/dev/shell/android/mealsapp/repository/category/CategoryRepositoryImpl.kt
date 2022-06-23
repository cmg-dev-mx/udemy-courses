package mx.dev.shell.android.mealsapp.repository.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import mx.dev.shell.android.mealsapp.core.model.CategoryBo
import mx.dev.shell.android.mealsapp.repository.category.map.CategoryMap
import mx.dev.shell.android.mealsapp.web.service.MealService

class CategoryRepositoryImpl constructor(
    private val service: MealService,
    private val map: CategoryMap
) : CategoryRepository {

    override suspend fun getNotes(): Flow<Result<List<CategoryBo>>> =
        service.getCategories().map {
            try {
                if (it.isSuccess) {
                    Result.success(
                        map.fromServiceListToBo(
                            it.getOrNull().orEmpty()
                        )
                    )
                } else {
                    Result.failure(it.exceptionOrNull()!!)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}