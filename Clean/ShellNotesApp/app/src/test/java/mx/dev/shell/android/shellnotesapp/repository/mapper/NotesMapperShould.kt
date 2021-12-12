package mx.dev.shell.android.shellnotesapp.repository.mapper

import kotlinx.coroutines.ExperimentalCoroutinesApi
import mx.dev.shell.android.shellnotesapp.core.model.NoteBo
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import mx.dev.shell.android.shellnotesapp.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class NotesMapperShould : BaseUnitTest() {

    private val noteMapper = mock(NoteMapper::class.java)

    private val noteDo = NoteDo("title", "content", 1635631241, 1L)
    private val noteBo = NoteBo(1L, "title", "content", 1635631241)

    @Test
    fun keepSameId() {
        val mapper = mockSuccessCase()
        val noteBoMapped = mapper(listOf(noteDo))[0]
        assertEquals(noteDo.id, noteBoMapped.id)
    }

    @Test
    fun keepSameTitle() {
        val mapper = mockSuccessCase()
        val noteBoMapped = mapper(listOf(noteDo))[0]
        assertEquals(noteDo.title, noteBoMapped.title)
    }

    @Test
    fun keepSameContent() {
        val mapper = mockSuccessCase()
        val noteBoMapped = mapper(listOf(noteDo))[0]
        assertEquals(noteDo.content, noteBoMapped.content)
    }

    @Test
    fun keepSameLastUpdate() {
        val mapper = mockSuccessCase()
        val noteBoMapped = mapper(listOf(noteDo))[0]
        assertEquals(noteDo.lastUpdate, noteBoMapped.lastUpdate)
    }

    private fun mockSuccessCase(): NotesMapper {
        `when`(noteMapper.fromDoToBo(noteDo)).thenReturn(noteBo)
        return NotesMapper(noteMapper)

    }
}