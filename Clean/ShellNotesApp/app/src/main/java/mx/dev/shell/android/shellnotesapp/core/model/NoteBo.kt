package mx.dev.shell.android.shellnotesapp.core.model

import java.text.SimpleDateFormat
import java.util.*

data class NoteBo(
    var id: Long,
    var title: String,
    var content: String,
    val lastUpdate: Long
) {
    fun lastUpdateFormatted() = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ROOT)
        .format(lastUpdate) ?: ""
}
