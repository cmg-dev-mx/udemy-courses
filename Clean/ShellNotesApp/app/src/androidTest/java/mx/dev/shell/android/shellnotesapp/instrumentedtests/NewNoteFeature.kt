package mx.dev.shell.android.shellnotesapp.instrumentedtests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import mx.dev.shell.android.shellnotesapp.R
import mx.dev.shell.android.shellnotesapp.utils.BaseUiTest
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NewNoteFeature : BaseUiTest() {

    @Test
    fun test01_displayNewNoteTitle() {
        navigateToNewNote()
        assertDisplayed("New note")
    }

    @Test
    fun test02_displayIncompleteErrorMessage() {
        navigateToNewNote()
        onView(withId(R.id.note_save_btn)).perform(click())
        assertDisplayed("Incomplete data")
    }

    @Test
    fun test03_displayNoteSavedMessage() {
        navigateToNewNote()
        createNote("Nota 1", "Descripcion de la nota")
        assertDisplayed("Note saved!")
    }

    @Test
    fun test04_navigateToNotesScreenAfterSave() {
        navigateToNewNote()
        createNote("Nota 1", "Descripcion de la nota")
        assertDisplayed(R.id.notes_container)
    }

    @Test
    fun test05_displayNotesSavedAfterSave() {
        navigateToNewNote()
        val title = "Nota guardada"
        createNote(title, "Descripcion de la nota")
        assertDisplayed(title)
    }

    @Test
    fun test06_hideDeleteButtonWhenNewNote() {
        navigateToNewNote()
        assertNotDisplayed(R.id.note_delete_btn)
    }

    private fun createNote(title: String, content: String) {
        onView(withId(R.id.note_title_edt)).perform(clearText(), typeText(title))
        onView(withId(R.id.note_content_edt)).perform(
            clearText(),
            typeText(content),
            closeSoftKeyboard()
        )
        onView(withId(R.id.note_save_btn)).perform(click())
    }

    private fun navigateToNewNote() {
        onView(withId(R.id.notes_newNote_btn)).perform(click())
    }
}