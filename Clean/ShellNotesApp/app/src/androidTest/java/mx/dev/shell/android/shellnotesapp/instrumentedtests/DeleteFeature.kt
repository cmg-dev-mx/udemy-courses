package mx.dev.shell.android.shellnotesapp.instrumentedtests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import mx.dev.shell.android.shellnotesapp.R
import mx.dev.shell.android.shellnotesapp.utils.BaseUiTest
import mx.dev.shell.android.shellnotesapp.utils.nthChildOf
import org.hamcrest.CoreMatchers
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DeleteFeature : BaseUiTest() {

    @Test
    fun test01_showDeleteButtonWhenUpdateNote() {
        createNote()
        gotoNoteDetail()
        assertDisplayed(R.id.note_delete_btn)
    }

    @Test
    fun test02_showDeleteConfirmation() {
        gotoNoteDetail()
        onView(withId(R.id.note_delete_btn)).perform(click())
        assertDisplayed("Are you sure do you want to delete the note?")
    }

    @Test
    fun test03_hideConfirmationWhenCancelDelete() {
        gotoNoteDetail()
        onView(withId(R.id.note_delete_btn)).perform(click())
        onView(withId(android.R.id.button2)).perform(click())
        onView(withText("Are you sure do you want to delete the note?")).check(doesNotExist())
    }

    @Test
    fun test04_displayConfirmationWhenDeleteNote() {
        gotoNoteDetail()
        onView(withId(R.id.note_delete_btn)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())
        assertDisplayed("Note deleted successfully")
    }

    @Test
    fun test05_deleteNote() {
        createNote()
        gotoNoteDetail()
        onView(withId(R.id.note_delete_btn)).perform(click())
        onView(withId(android.R.id.button1)).perform(click())
        assertNotDisplayed(R.id.notes_notes_rec)
    }

    private fun gotoNoteDetail() {
        onView(
            CoreMatchers.allOf(
                withId(R.id.item_note_container),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        withId(R.id.notes_notes_rec),
                        0
                    )
                )
            )
        ).perform(click())
    }

    private fun createNote() {
        onView(withId(R.id.notes_newNote_btn)).perform(click())

        onView(withId(R.id.note_title_edt)).perform(
            ViewActions.clearText(),
            ViewActions.typeText("Nota 1")
        )
        onView(withId(R.id.note_content_edt)).perform(
            ViewActions.clearText(),
            ViewActions.typeText("Descripcion de la nota 1"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.note_save_btn)).perform(click())
    }
}