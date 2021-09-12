package mx.dev.shell.android.coroutinesroom.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
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
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setupListeners()
        observeViewModel()
        return binding.root
    }

    private fun setupListeners() = binding.apply {
        loginBtnLogin.setOnClickListener {
            (context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)!!
                .hideSoftInputFromWindow(loginBtnLogin.windowToken, 0)
            onLogin(it)
        }
        loginBtnSignup.setOnClickListener {
            (context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)!!
                .hideSoftInputFromWindow(loginBtnSignup.windowToken, 0)
            onGotoSignUp(it)
        }
    }

    private fun observeViewModel() = viewModel.apply {
        loginComplete.observe(this@LoginFragment as LifecycleOwner) { loginCompleted ->
            val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
            Navigation.findNavController(binding.loginContainer)
                .navigate(action)
        }
        error.observe(this@LoginFragment as LifecycleOwner) { error ->
            showMessage(getString(R.string.login_error_message, error))
        }
    }

    private fun onLogin(v: View) {
        val user = binding.loginTilUser.editText?.text.toString()
        val password = binding.loginTilPassword.editText?.text.toString()

        if (user.isEmpty() || password.isEmpty()) {
            showMessage(getString(R.string.login_error_fillFields))
        } else {
            viewModel.login(user, password)
        }
    }

    private fun onGotoSignUp(v: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
        Navigation.findNavController(v).navigate(action)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.loginContainer,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}