package mx.dev.shell.android.coroutinesroom.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.dev.shell.android.coroutinesroom.model.LoginState
import mx.dev.shell.android.coroutinesroom.model.UserDataBase
import mx.dev.shell.android.coroutinesroom.model.UserDataBase_Impl

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDataBase.getInstance(getApplication()).userDao() }

    val logout = MutableLiveData<Boolean>()
    val userDeleted = MutableLiveData<Boolean>()

    fun logout() {
        LoginState.logout()
        logout.value = true
    }

    fun deleteUser() {
        coroutineScope.launch {
            LoginState.user?.let { user ->
                db.deleteUser(user.id)
            }
            withContext(Dispatchers.Main) {
                LoginState.logout()
                userDeleted.value = true
            }
        }
    }
}
