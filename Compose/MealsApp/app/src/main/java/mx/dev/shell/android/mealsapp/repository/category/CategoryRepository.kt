package mx.dev.shell.android.mealsapp.repository.category

import kotlinx.coroutines.flow.Flow
import mx.dev.shell.android.mealsapp.core.model.CategoryBo

interface CategoryRepository {
    suspend fun getNotes(): Flow<Result<List<CategoryBo>>>
}
