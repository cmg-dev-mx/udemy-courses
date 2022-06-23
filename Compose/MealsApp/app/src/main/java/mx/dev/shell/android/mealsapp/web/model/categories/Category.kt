package mx.dev.shell.android.mealsapp.web.model.categories


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory")
    var idCategory: String? = null,
    @SerializedName("strCategory")
    var strCategory: String? = null,
    @SerializedName("strCategoryDescription")
    var strCategoryDescription: String? = null,
    @SerializedName("strCategoryThumb")
    var strCategoryThumb: String? = null
)