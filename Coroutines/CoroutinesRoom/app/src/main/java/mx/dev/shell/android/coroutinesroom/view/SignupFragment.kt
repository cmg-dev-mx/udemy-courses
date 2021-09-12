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
import mx.dev.shell.android.coroutinesroom.databinding.FragmentSignupBinding
import mx.dev.shell.android.coroutinesroom.vm.LoginViewModel

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setupListeners()
        observeViewModel()
        return binding.root
    }

    private fun setupListeners() = binding.apply {
        signupBtnSignup.setOnClickListener {
            hideKeyboard()
            onSignup()
        }
        signupBtnLogin.setOnClickListener {
            hideKeyboard()
            gotoLogin()
        }
    }

    private fun observeViewModel() = viewModel.apply {
        signupComplete.observe(this@SignupFragment as LifecycleOwner) {
            gotoMain()
        }
        error.observe(this@SignupFragment as LifecycleOwner) { error ->
            showMessage(getString(R.string.signup_error_message, error))
        }
    }

    private fun onSignup() {
        val user = binding.signupTilUser.editText?.text.toString()
        val password = binding.signupTilPassword.editText?.text.toString()
        val info = binding.signupTilOtherInfo.editText?.text.toString()

        if (user.isEmpty() || password.isEmpty() || info.isEmpty()) {
            showMessage(getString(R.string.signup_error_fillFields))
        } else {
            viewModel.signup(user, password, info)
        }

    }

    private fun gotoMain() {
        val action = SignupFragmentDirections.actionSignupFragmentToMainActivity()
        Navigation.findNavController(binding.signupContainer).navigate(action)
    }

    private fun gotoLogin() {
        val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
        Navigation.findNavController(binding.signupContainer).navigate(action)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.signupContainer,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun hideKeyboard() {
        (context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)!!
            .hideSoftInputFromWindow(binding.signupContainer.windowToken, 0)
    }
}