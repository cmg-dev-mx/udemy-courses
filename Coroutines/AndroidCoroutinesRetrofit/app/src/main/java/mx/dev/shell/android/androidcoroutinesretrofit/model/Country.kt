package mx.dev.shell.android.androidcoroutinesretrofit.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val country: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("flagPNG")
    val flag: String?
)