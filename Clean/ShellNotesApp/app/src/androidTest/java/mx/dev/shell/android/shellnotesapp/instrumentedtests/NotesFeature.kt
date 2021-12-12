package mx.dev.shell.android.shellnotesapp.instrumentedtests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import mx.dev.shell.android.shellnotesapp.R
import mx.dev.shell.android.shellnotesapp.utils.BaseUiTest
import mx.dev.shell.android.shellnotesapp.utils.getText
import mx.dev.shell.android.shellnotesapp.utils.nthChildOf
import org.hamcrest.CoreMatchers.allOf
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.text.SimpleDateFormat
import java.util.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class NotesFeature : BaseUiTest() {

    @Test
    fun test01_displayScreenTitle() {
        assertDisplayed("Notes App")
    }

    @Test
    fun test02_displayEmptyMessageInEmptyList() {
        assertNotDisplayed(R.id.notes_notes_rec)
        assertDisplayed("Without notes. To add one,\npush the + button.")
    }

    @Test
    fun test03_navigateToNewNoteScreen() {
        onView(withId(R.id.notes_newNote_btn)).perform(click())
        assertDisplayed(R.id.note_container)
    }

    @Test
    fun test04_displayLastUpdateFormatted() {
        createNote()
        val lastUpdate = ((onView(
            allOf(
                withId(R.id.item_note_updatedBy),
                isDescendantOfA(nthChildOf(withId(R.id.notes_notes_rec), 0))
            )
        ))).getText()
        assert(validateDate(lastUpdate))
    }

    @Test
    fun test05_navigateToDetailScreen() {
        createNote()
        gotoNoteDetail()
        assertDisplayed(R.id.note_container)
    }

    @Test
    fun test06_showNoteDetail() {
        createNote()
        gotoNoteDetail()
        assertDisplayed("Nota 1")
        assertDisplayed("Descripcion de la nota 1")
    }

    private fun gotoNoteDetail() {
        onView(
            allOf(
                withId(R.id.item_note_container),
                isDescendantOfA(nthChildOf(withId(R.id.notes_notes_rec), 0))
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

    private fun validateDate(date: String) = try {
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ROOT)
        format.parse(date.split(": ")[1])
        true
    } catch (e: Exception) {
        false
    }
}