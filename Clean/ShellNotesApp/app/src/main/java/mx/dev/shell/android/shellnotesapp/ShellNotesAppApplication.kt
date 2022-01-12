package mx.dev.shell.android.shellnotesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class ShellNotesAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}