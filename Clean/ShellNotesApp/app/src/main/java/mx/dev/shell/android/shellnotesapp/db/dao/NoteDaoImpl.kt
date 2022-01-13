package mx.dev.shell.android.shellnotesapp.db.dao

import io.realm.Realm
import io.realm.kotlin.where
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import javax.inject.Inject

class NoteDaoImpl @Inject constructor(private val db: Realm) : NoteDao {

    override suspend fun saveNote(note: NoteDo): Long {
        db.executeTransaction {
            it.insert(note)
        }
        return note.id
    }

    override suspend fun getAllNotes(): List<NoteDo> {
        return db.where<NoteDo>().findAll()
    }

    override suspend fun getNoteById(id: Long): NoteDo {
        return db.where<NoteDo>().equalTo("id", id).findFirst()!!
    }

    override suspend fun deleteNote(id: Long): Int {
        var success = 0
        db.executeTransaction {
            val noteToDelete = it.where<NoteDo>().equalTo("id", id).findFirst()!!
            noteToDelete.deleteFromRealm()
            if (it.where<NoteDo>().equalTo("id", id).findFirst() == null) {
                success = 1
            }
        }
        return success
    }
}