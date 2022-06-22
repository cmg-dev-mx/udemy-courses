package mx.dev.shell.android.mealsapp.flow.categories.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mx.dev.shell.android.mealsapp.core.model.CategoryBo
import mx.dev.shell.android.mealsapp.repository.category.CategoryRepository

class CategoriesViewModel constructor(private val repository: CategoryRepository) : ViewModel() {

    val showProgressbar = MutableLiveData<Boolean>()
    val categories = MutableLiveData<List<CategoryBo>>()
    val errorMessage = MutableLiveData<String>()

    fun getNotes() {
        viewModelScope.launch {
            showProgressbar.value = true
            repository.getNotes()
                .onEach {
                    showProgressbar.value = false
                }
                .collect {
                    if (it.isSuccess) {
                        categories.value = it.getOrNull().orEmpty()
                    } else {
                        errorMessage.value = it.exceptionOrNull()?.message ?: "Unknown error"
                    }
                }
        }
    }
}