package mx.dev.shell.android.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import mx.dev.shell.android.groovy.utils.nthChildOf
import mx.dev.shell.android.groovy.utils.withDrawable
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaylistsFeature {

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun displayScreenTitle() {
        onView(withText("Playlists"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysPlaylists() {
        Thread.sleep(4000)


        onView(withId(R.id.playlists_list_recycler))
            .check(matches(hasChildCount(10)))
        onView(
            allOf(
                withId(R.id.playlist_name),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list_recycler), 0))))
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlist_name),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list_recycler), 0))))
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlist_name),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list_recycler), 0))))
            .check(matches(withDrawable(R.drawable.playlist)))
            .check(matches(isDisplayed()))


    }
}