package mx.dev.shell.android.shellnotesapp.instrumentedtests

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions
import mx.dev.shell.android.shellnotesapp.R
import mx.dev.shell.android.shellnotesapp.utils.BaseUiTest
import mx.dev.shell.android.shellnotesapp.utils.nthChildOf
import org.hamcrest.CoreMatchers
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UpdateNoteFeature : BaseUiTest() {

    @Test
    fun test01_updateNote() {
        createNote()
        Thread.sleep(3000)
        updateNote()
        Thread.sleep(500)
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.notes_notes_rec, 1)
        gotoNoteDetail()
        BaristaVisibilityAssertions.assertDisplayed("Nota 1 modificada")
        BaristaVisibilityAssertions.assertDisplayed("Nota 1 modificada")
        BaristaVisibilityAssertions.assertDisplayed("Descripcion de la nota 1 modificada")
    }

    private fun updateNote() {
        gotoNoteDetail()
        Espresso.onView(ViewMatchers.withId(R.id.note_title_edt)).perform(
            ViewActions.clearText(),
            ViewActions.typeText("Nota 1 modificada")
        )
        Espresso.onView(ViewMatchers.withId(R.id.note_content_edt)).perform(
            ViewActions.clearText(),
            ViewActions.typeText("Descripcion de la nota 1 modificada"),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(ViewMatchers.withId(R.id.note_save_btn)).perform(ViewActions.click())
    }

    private fun gotoNoteDetail() {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.item_note_container),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.notes_notes_rec),
                        0
                    )
                )
            )
        ).perform(ViewActions.click())
    }

    private fun createNote() {
        Espresso.onView(ViewMatchers.withId(R.id.notes_newNote_btn)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.note_title_edt)).perform(
            ViewActions.clearText(),
            ViewActions.typeText("Nota 1")
        )
        Espresso.onView(ViewMatchers.withId(R.id.note_content_edt)).perform(
            ViewActions.clearText(),
            ViewActions.typeText("Descripcion de la nota 1"),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(ViewMatchers.withId(R.id.note_save_btn)).perform(ViewActions.click())
    }
}