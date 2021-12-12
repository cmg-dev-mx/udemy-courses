package mx.dev.shell.android.shellnotesapp.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteDo(
    @ColumnInfo(name = "note_title")
    var title: String,
    @ColumnInfo(name = "note_content")
    var content: String,
    @ColumnInfo(name = "note_last_update")
    var lastUpdate: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
)
