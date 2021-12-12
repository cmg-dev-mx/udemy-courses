package mx.dev.shell.android.shellnotesapp.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import mx.dev.shell.android.shellnotesapp.app.flow.notes.MainActivity
import mx.dev.shell.android.shellnotesapp.db.base.NoteDatabase
import mx.dev.shell.android.shellnotesapp.db.model.NoteDo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class NoteDaoShould {

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var database: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.noteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertNotesAndGetAListWithNotes() = runBlocking {
        val note1 = NoteDo("title 1", "content 1", 100L)
        dao.saveNote(note1)
        var notes = dao.getAllNotes()
        assert(notes.last().title == note1.title && notes.last().content == note1.content)

        val note2 = NoteDo("title 2", "content 2", 110L)
        dao.saveNote(note2)
        notes = dao.getAllNotes()
        assert(notes.last().title == note2.title && notes.last().content == note2.content)
    }

    @Test
    fun insertNoteAndGetId() = runBlocking {
        val note1 = NoteDo("title 1", "content 1", 100L)
        val id = dao.saveNote(note1)
        assert(1L == id)
    }

    @Test
    fun getNoteById() = runBlocking {
        val note1 = NoteDo("title 1", "content 1", 100L)
        val id = dao.saveNote(note1)
        val noteLoaded = dao.getNoteById(id)
        assert(
            noteLoaded.title == note1.title
                    && noteLoaded.content == note1.content
                    && noteLoaded.lastUpdate == note1.lastUpdate
                    && noteLoaded.id == id
        )
    }

    @Test
    fun deleteNoteById() = runBlocking {
        val note1 = NoteDo("title 1", "content 1", 100L)
        val id = dao.saveNote(note1)
        val noteLoaded = dao.getNoteById(id)
        val rowsaffected = dao.deleteNote(noteLoaded.id)
        assert(1 == rowsaffected)
    }
}