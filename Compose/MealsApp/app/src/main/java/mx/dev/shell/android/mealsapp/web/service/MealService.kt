package mx.dev.shell.android.mealsapp.web.service

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.mealsapp.web.model.categories.Category

interface MealService {
    suspend fun getCategories() : Flow<Result<List<Category>>>
}
