package mx.dev.shell.android.coroutinesroom.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val logout = MutableLiveData<Boolean>()
    val userDeleted = MutableLiveData<Boolean>()

    fun logout() {

    }

    fun deleteUser() {

    }
}
