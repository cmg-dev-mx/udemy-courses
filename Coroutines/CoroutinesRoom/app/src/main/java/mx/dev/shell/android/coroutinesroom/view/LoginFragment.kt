package mx.dev.shell.android.coroutinesroom.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import mx.dev.shell.android.coroutinesroom.R
import mx.dev.shell.android.coroutinesroom.databinding.FragmentLoginBinding
import mx.dev.shell.android.coroutinesroom.vm.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setupListeners()
        observeViewModel()
        return binding.root
    }

    private fun setupListeners() = binding.apply {
        loginBtnLogin.setOnClickListener { onLogin(it) }
        loginBtnSignup.setOnClickListener { onGotoSignUp(it) }
    }

    private fun observeViewModel() = viewModel.apply {
        loginComplete.observe(this@LoginFragment as LifecycleOwner) {
            // TODO
        }
        error.observe(this@LoginFragment as LifecycleOwner) {
            // TODO
        }
    }

    private fun onLogin(v: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        Navigation.findNavController(v).navigate(action)
    }

    private fun onGotoSignUp(v: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
        Navigation.findNavController(v).navigate(action)
    }
}