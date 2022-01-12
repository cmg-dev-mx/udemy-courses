package mx.dev.shell.android.shellnotesapp.db.base

import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class RealmProvider @Inject constructor() {

    init {
        val configuration = RealmConfiguration.Builder()
            .name("notes.realm")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }

    companion object {
        fun getDb(): Realm {
            return Realm.getDefaultInstance()
        }
    }
}