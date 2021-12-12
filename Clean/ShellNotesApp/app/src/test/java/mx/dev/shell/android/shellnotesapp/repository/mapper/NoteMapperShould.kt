package mx.dev.shell.android.shellnotesapp.repository.mapper

import kotlinx.coroutines.ExperimentalCoroutinesApi
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import mx.dev.shell.android.shellnotesapp.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteMapperShould : BaseUnitTest() {

    private val mapper = NoteMapper()

    private val noteBo = NoteBo(1L, "title", "content", 1635631241)
    private val noteDoMapped = mapper.fromBoToDo(noteBo)

    private val noteDo = NoteDo("title", "content", 1635631241, 1L)
    private val noteBoMapped = mapper.fromDoToBo(noteDo)

    @Test
    fun keepSameIdFromBoToDo() {
        assertEquals(noteBo.id, noteDoMapped.id)
    }

    @Test
    fun keepSameTitleFromBoToDo() {
        assertEquals(noteBo.title, noteDoMapped.title)
    }

    @Test
    fun keepSameContentFromBoToDo() {
        assertEquals(noteBo.content, noteDoMapped.content)
    }

    @Test
    fun keepSameLastUpdateFromBoToDo() {
        assertEquals(noteBo.lastUpdate, noteDoMapped.lastUpdate)
    }

    @Test
    fun keepSameIdFromDoToBo() {
        assertEquals(noteDo.id, noteBoMapped.id)
    }

    @Test
    fun keepSameTitleFromDoToBo() {
        assertEquals(noteDo.title, noteBoMapped.title)
    }

    @Test
    fun keepSameContentFromDoToBo() {
        assertEquals(noteDo.content, noteBoMapped.content)
    }

    @Test
    fun keepSameLastUpdateFromDoToBo() {
        assertEquals(noteDo.lastUpdate, noteBoMapped.lastUpdate)
    }
}