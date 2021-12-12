package mx.dev.shell.android.shellnotesapp.repository.mapper

import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import javax.inject.Inject

class NotesMapper @Inject constructor(private val noteMapper: NoteMapper) :
    Function1<List<NoteDo>, List<NoteBo>> {

    override fun invoke(notesDo: List<NoteDo>) = notesDo.map {
        noteMapper.fromDoToBo(it)
    }
}
