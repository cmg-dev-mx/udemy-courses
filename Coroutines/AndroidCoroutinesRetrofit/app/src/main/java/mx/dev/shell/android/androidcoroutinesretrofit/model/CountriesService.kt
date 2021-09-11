package mx.dev.shell.android.androidcoroutinesretrofit.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }

    fun getCountriesService(): CountriesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
}