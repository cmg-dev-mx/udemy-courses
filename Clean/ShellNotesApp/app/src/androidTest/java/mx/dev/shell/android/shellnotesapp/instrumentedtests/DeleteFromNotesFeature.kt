package mx.dev.shell.android.shellnotesapp.instrumentedtests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import mx.dev.shell.android.shellnotesapp.R
import mx.dev.shell.android.shellnotesapp.utils.nthChildOf
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class DeleteFromNotesFeature {

    @Test
    fun test01_showDeleteNoteWhenSelectNote() {
        createNote("Nota a borrar", "Descripcion de la nota")
        gotoDetail()
        assertDisplayed("Are you sure do you want to delete the note?")
    }

    @Test
    fun test02_closeDialogOnCancelDeleteNote() {
        gotoDetail()
        onView(withId(android.R.id.button2)).perform(click())
        onView(withText("Are you sure do you want to delete the note?"))
            .check(doesNotExist())
    }

    @Test
    fun test03_deleteNote() {
        gotoDetail()
        onView(withId(android.R.id.button1)).perform(click())
        assertDisplayed("Note deleted successfully")
    }

    @Test
    fun test04_displayDeleteNoteConfirmation() {
        createNote("Nota a borrar 2", "Descripcion de la nota")
        gotoDetail()
        onView(withId(android.R.id.button1)).perform(click())
        assertDisplayed("Note deleted successfully")
    }

    @Test
    fun test05_refreshListAfterDeleteNote() {
        createNote("Nota a borrar 3", "Descripcion de la nota")
        gotoDetail()
        onView(withId(android.R.id.button1)).perform(click())
        onView(withText("Nota a borrar 3")).check(doesNotExist())
    }

    private fun gotoDetail() {
        onView(
            allOf(
                withId(R.id.item_note_container),
                isDescendantOfA(
                    nthChildOf(
                        withId(R.id.notes_notes_rec),
                        0
                    )
                )
            )
        ).perform(longClick())
    }

    private fun createNote(title: String, content: String) {
        onView(withId(R.id.notes_newNote_btn)).perform(click())
        onView(withId(R.id.note_title_edt)).perform(
            clearText(),
            typeText(title)
        )
        onView(withId(R.id.note_content_edt)).perform(
            clearText(),
            typeText(content),
            closeSoftKeyboard()
        )
        onView(withId(R.id.note_save_btn)).perform(click())
    }
}