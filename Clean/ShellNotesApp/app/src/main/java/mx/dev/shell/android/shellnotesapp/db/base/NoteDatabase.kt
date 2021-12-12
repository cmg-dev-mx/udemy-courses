package mx.dev.shell.android.shellnotesapp.db.base

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.dev.shell.android.shellnotesapp.db.dao.NoteDao
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo

@Database(
    entities = [
        NoteDo::class
    ],
    version = NoteDatabase.VERSION,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "note-db"
        const val VERSION = 1
    }
}
