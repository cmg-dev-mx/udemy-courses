package mx.dev.shell.android.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        signupBtnSignup.setOnClickListener { onSignup(it) }
        signupBtnLogin.setOnClickListener { onGotoLogin(it) }
    }

    private fun observeViewModel() = viewModel.apply {
        signupComplete.observe(this@SignupFragment as LifecycleOwner) { isComplete ->
            val action = SignupFragmentDirections.actionSignupFragmentToMainActivity()
            Navigation.findNavController(binding.signupContainer).navigate(action)
        }
        error.observe(this@SignupFragment as LifecycleOwner) { error ->
            showMessage(getString(R.string.signup_error_message, error))
        }
    }

    private fun onSignup(v: View) {
        val user = binding.signupTilUser.editText?.text.toString()
        val password = binding.signupTilPassword.editText?.text.toString()
        val info = binding.signupTilOtherInfo.editText?.text.toString()

        if (user.isEmpty() || password.isEmpty() || info.isEmpty()) {
            showMessage(getString(R.string.signup_error_fillFields))
        } else {
            viewModel.signup(user, password, info)
        }

    }

    private fun onGotoLogin(v: View) {
        val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
        Navigation.findNavController(v).navigate(action)
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.signupContainer,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}