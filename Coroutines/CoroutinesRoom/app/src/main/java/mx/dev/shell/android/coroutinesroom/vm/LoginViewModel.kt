package mx.dev.shell.android.coroutinesroom.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    val signupComplete = MutableLiveData<Boolean>()
    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(user: String, password: String, info: String) {

    }

    fun login(user: String, password: String) {

    }
}
