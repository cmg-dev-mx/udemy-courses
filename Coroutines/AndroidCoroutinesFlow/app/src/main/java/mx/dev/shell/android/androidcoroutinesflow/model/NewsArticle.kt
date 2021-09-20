package mx.dev.shell.android.androidcoroutinesflow.model

import com.google.gson.annotations.SerializedName

data class NewsArticle(
    val author: String? = null,
    @SerializedName("desription") val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    @SerializedName("imageUrl") val urlToImage: String? = null
)
