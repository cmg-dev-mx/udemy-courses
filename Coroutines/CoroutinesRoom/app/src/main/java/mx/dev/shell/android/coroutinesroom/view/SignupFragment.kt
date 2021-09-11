package mx.dev.shell.android.coroutinesroom.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
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
            // TODO
        }
        error.observe(this@SignupFragment as LifecycleOwner) { isError ->
            // TODO
        }
    }

    private fun onSignup(v: View) {
        val action = SignupFragmentDirections.actionSignupFragmentToMainActivity()
        Navigation.findNavController(v).navigate(action)
    }

    private fun onGotoLogin(v: View) {
        val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
        Navigation.findNavController(v).navigate(action)
    }
}