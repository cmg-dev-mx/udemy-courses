package mx.dev.shell.android.shellnotesapp.db.dao

import mx.dev.shell.android.shellnotesapp.db.model.NoteDo

interface NoteDao {

    suspend fun saveNote(note: NoteDo): Long
    suspend fun getAllNotes(): List<NoteDo>
    suspend fun getNoteById(id: Long): NoteDo
    suspend fun deleteNote(id: Long): Int
}
