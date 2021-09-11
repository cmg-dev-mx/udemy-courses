package mx.dev.shell.android.androidcoroutinesretrofit.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.dev.shell.android.androidcoroutinesretrofit.databinding.ItemCountryBinding
import mx.dev.shell.android.androidcoroutinesretrofit.loadImage
import mx.dev.shell.android.androidcoroutinesretrofit.model.Country

class CountriesAdapter(private val countries: ArrayList<Country>): RecyclerView.Adapter<CountriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountriesViewHolder(
        ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) =
        holder.bind(countries[position])

    override fun getItemCount() = countries.size

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }
}

class CountriesViewHolder(private val binding: ItemCountryBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        binding.apply {
            countryName.text = country.country
            countryCapital.text = country.capital
            countryImage.loadImage(country.flag)
        }
    }

}
