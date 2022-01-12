package mx.dev.shell.android.shellnotesapp.db.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class NoteDo(
    var title: String = "",
    var content: String = "",
    var lastUpdate: Long = 0L,
    @PrimaryKey
    var id: Long = Date().time
) : RealmObject()
