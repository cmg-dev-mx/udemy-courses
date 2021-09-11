package mx.dev.shell.android.androidcoroutinesretrofit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.dev.shell.android.androidcoroutinesretrofit.databinding.FragmentCountriesBinding
import mx.dev.shell.android.androidcoroutinesretrofit.vm.CountriesViewModel

class CountriesFragment : Fragment() {

    private lateinit var binding: FragmentCountriesBinding
    private lateinit var viewModel: CountriesViewModel

    private val countriesAdapter = CountriesAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesBinding.inflate(inflater, container, false)

        setupView()

        viewModel = ViewModelProvider(this).get(CountriesViewModel::class.java)
        viewModel.refresh()

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this as LifecycleOwner) { countries ->
            countries?.let {
                binding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        }

        viewModel.countryLoadError.observe(this as LifecycleOwner) { isError ->
            binding.countriesErrorLbl.visibility = if (isError == null) View.GONE else View.VISIBLE
        }

        viewModel.loading.observe(this as LifecycleOwner) { isLoading ->
            isLoading?.let {
                binding.countriesProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.countriesList.visibility = View.GONE
                    binding.countriesErrorLbl.visibility = View.GONE
                }
            }
        }
    }

    private fun setupView() {
        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }
    }
}