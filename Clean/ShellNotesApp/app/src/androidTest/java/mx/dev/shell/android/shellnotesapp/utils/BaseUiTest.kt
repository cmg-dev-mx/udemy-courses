package mx.dev.shell.android.shellnotesapp.utils

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import mx.dev.shell.android.shellnotesapp.ShellNotesAppApplication
import mx.dev.shell.android.shellnotesapp.app.flow.notes.MainActivity
import mx.dev.shell.android.shellnotesapp.db.base.NoteDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUiTest {

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        if (ApplicationProvider.getApplicationContext<ShellNotesAppApplication>().applicationContext.databaseList()
                .contains(NoteDatabase.DATABASE_NAME)
        ) {
            ApplicationProvider.getApplicationContext<ShellNotesAppApplication>().deleteDatabase(
                NoteDatabase.DATABASE_NAME
            )
        }
    }

    @After
    fun tearDown() {
        ApplicationProvider.getApplicationContext<ShellNotesAppApplication>().deleteDatabase(
            NoteDatabase.DATABASE_NAME
        )
    }
}