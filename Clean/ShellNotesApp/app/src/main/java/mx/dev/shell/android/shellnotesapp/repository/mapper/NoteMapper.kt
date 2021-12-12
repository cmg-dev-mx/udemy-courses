package mx.dev.shell.android.shellnotesapp.repository.mapper

import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import javax.inject.Inject

class NoteMapper @Inject constructor() {

    fun fromBoToDo(noteBo: NoteBo) = NoteDo(
        title = noteBo.title,
        content = noteBo.content,
        lastUpdate = noteBo.lastUpdate,
        id = noteBo.id
    )

    fun fromDoToBo(noteDo: NoteDo) = NoteBo(
        id = noteDo.id,
        title = noteDo.title,
        content = noteDo.content,
        lastUpdate = noteDo.lastUpdate
    )
}
