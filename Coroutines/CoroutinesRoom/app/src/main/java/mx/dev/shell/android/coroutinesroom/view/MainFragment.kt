package mx.dev.shell.android.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import mx.dev.shell.android.coroutinesroom.R
import mx.dev.shell.android.coroutinesroom.databinding.FragmentMainBinding
import mx.dev.shell.android.coroutinesroom.vm.MainViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupListeners()
        observeViewModel()
        return binding.root
    }

    private fun setupListeners() = binding.apply {
        mainBtnLogout.setOnClickListener { onLogout(it) }
        mainBtnDelete.setOnClickListener { onDelete(it) }
    }

    private fun observeViewModel() = viewModel.apply {
        logout.observe(this@MainFragment as LifecycleOwner) { logout ->

        }
        userDeleted.observe(this@MainFragment as LifecycleOwner) { isDeleted ->

        }
    }

    private fun onLogout(v: View) {
        val action = MainFragmentDirections.actionMainFragmentToLoginActivity()
        Navigation.findNavController(v).navigate(action)
    }

    private fun onDelete(v: View) {
        val action = MainFragmentDirections.actionMainFragmentToLoginActivity()
        Navigation.findNavController(v).navigate(action)
    }
}