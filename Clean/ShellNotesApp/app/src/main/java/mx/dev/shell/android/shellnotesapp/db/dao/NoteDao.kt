package mx.dev.shell.android.shellnotesapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: NoteDo): Long

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<NoteDo>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Long): NoteDo

    @Query("DELETE FROM note WHERE id = :id")
    fun deleteNote(id: Long): Int
}
