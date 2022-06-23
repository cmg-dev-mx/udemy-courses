package mx.dev.shell.android.mealsapp.web.model.categories


import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories")
    var categories: List<Category?>? = null
)