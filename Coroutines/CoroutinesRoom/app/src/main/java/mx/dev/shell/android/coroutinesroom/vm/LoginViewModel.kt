package mx.dev.shell.android.coroutinesroom.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.dev.shell.android.coroutinesroom.model.LoginState
import mx.dev.shell.android.coroutinesroom.model.User
import mx.dev.shell.android.coroutinesroom.model.UserDataBase

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy {
        UserDataBase.getInstance(application.applicationContext)
            .userDao()
    }

    val signupComplete = MutableLiveData<Boolean>()
    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(username: String, password: String, info: String) {
        coroutineScope.launch {
            val user = db.getUser(username)

            if (user != null) {
                withContext(Dispatchers.Main) {
                    error.value = "User already exists"
                }
            } else {
                val newUser = User(username, password.hashCode(), info)
                val userId = db.insertUser(newUser)
                newUser.id = userId
                LoginState.login(newUser)
                withContext(Dispatchers.Main) {
                    signupComplete.value = true
                }
            }
        }
    }

    fun login(username: String, password: String) {
        coroutineScope.launch {
            val user = db.getUser(username)

            if (user == null || user.passwordHash != password.hashCode()) {
                withContext(Dispatchers.Main) {
                    error.value = "Wrong credentials"
                }
            } else {
                LoginState.user = user
                withContext(Dispatchers.Main) {
                    loginComplete.value = true
                }
            }
        }
    }
}
