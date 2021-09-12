package mx.dev.shell.android.coroutinesroom.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import mx.dev.shell.android.coroutinesroom.R
import mx.dev.shell.android.coroutinesroom.databinding.FragmentMainBinding
import mx.dev.shell.android.coroutinesroom.model.LoginState
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

        binding.mainTxtUser.text = LoginState.user?.userName

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
            gotoLogin()
        }
        userDeleted.observe(this@MainFragment as LifecycleOwner) { isDeleted ->
            gotoLogin()
        }
    }

    private fun onLogout(v: View) {
        viewModel.logout()
    }

    private fun onDelete(v: View) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.deleteDialog_title))
                .setMessage(getString(R.string.deleteDialog_question))
                .setPositiveButton(getString(R.string.deleteDialog_btn_accept)) { _, _ ->
                    viewModel.deleteUser()
                }
                .setNegativeButton(getString(R.string.deleteDialog_btn_cancel), null)
                .create()
                .show()
        }
    }

    private fun gotoLogin() {
        val action = MainFragmentDirections.actionMainFragmentToLoginActivity()
        Navigation.findNavController(binding.mainContainer)
            .navigate(action)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.mainContainer,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}