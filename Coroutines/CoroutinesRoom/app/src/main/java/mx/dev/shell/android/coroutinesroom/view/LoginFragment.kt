package mx.dev.shell.android.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import mx.dev.shell.android.coroutinesroom.R
import mx.dev.shell.android.coroutinesroom.databinding.FragmentLoginBinding
import mx.dev.shell.android.coroutinesroom.vm.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupListeners()
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        observeViewModel()
        return binding.root
    }

    private fun setupListeners() = binding.apply {
        loginBtnLogin.setOnClickListener {
            hideKeyboard()
            onLogin()
        }
        loginBtnSignup.setOnClickListener {
            hideKeyboard()
            gotoSignUp()
        }
    }

    private fun observeViewModel() = viewModel.apply {
        loginComplete.observe(this@LoginFragment as LifecycleOwner) {
            gotoMain()
        }
        error.observe(this@LoginFragment as LifecycleOwner) { error ->
            showMessage(getString(R.string.login_error_message, error))
        }
    }

    private fun onLogin() {
        val user = binding.loginTilUser.editText?.text.toString()
        val password = binding.loginTilPassword.editText?.text.toString()

        if (user.isEmpty() || password.isEmpty()) {
            showMessage(getString(R.string.login_error_fillFields))
        } else {
            viewModel.login(user, password)
        }
    }

    private fun gotoMain() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        Navigation.findNavController(binding.loginContainer)
            .navigate(action)
    }

    private fun gotoSignUp() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
        Navigation.findNavController(binding.loginContainer).navigate(action)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.loginContainer,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun hideKeyboard() {
        (context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)!!
            .hideSoftInputFromWindow(binding.loginContainer.windowToken, 0)
    }
}