package mx.dev.shell.android.androidcoroutinesretrofit.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.dev.shell.android.androidcoroutinesretrofit.model.Country

class CountriesViewModel: ViewModel() {

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true

        val dummyData = generateSummyCountries()

        countries.value = dummyData
        countryLoadError.value = null
        loading.value = false
    }

    private fun generateSummyCountries(): List<Country> {
        val countries = arrayListOf<Country>()
        countries.add(Country("Dummy Country 1", "Dummy capital 1", ""))
        countries.add(Country("Dummy Country 2", "Dummy capital 2", ""))
        countries.add(Country("Dummy Country 3", "Dummy capital 3", ""))
        countries.add(Country("Dummy Country 4", "Dummy capital 4", ""))
        countries.add(Country("Dummy Country 5", "Dummy capital 5", ""))

        return countries
    }

    private fun onError(message: String) {
        countryLoadError.value = message
        loading.value = false
    }
}
