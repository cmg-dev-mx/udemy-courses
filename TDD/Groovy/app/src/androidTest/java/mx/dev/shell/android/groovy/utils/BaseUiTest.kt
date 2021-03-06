package mx.dev.shell.android.groovy.utils

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import mx.dev.shell.android.groovy.MainActivity
import mx.dev.shell.android.groovy.playlists.idlingResource
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
        IdlingRegistry.getInstance()
            .register(idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance()
            .unregister(idlingResource)
    }
}