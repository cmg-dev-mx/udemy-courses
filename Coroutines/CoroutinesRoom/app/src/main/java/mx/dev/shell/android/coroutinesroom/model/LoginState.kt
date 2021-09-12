package mx.dev.shell.android.coroutinesroom.model

object LoginState {

    private var isLoggedIn = false
    var user: User? = null

    fun logout() {
        isLoggedIn = false
        user = null
    }

    fun login(user: User) {
        this.user = user
        isLoggedIn = true
    }
}