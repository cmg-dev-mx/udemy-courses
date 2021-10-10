package mx.dev.shell.android.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import mx.dev.shell.android.groovy.playlists.idlingResource
import mx.dev.shell.android.groovy.utils.BaseUiTest
import mx.dev.shell.android.groovy.utils.nthChildOf
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class PlaylistDetailFeature: BaseUiTest() {

    @Test
    fun displaysPlaylistNameAndDetails() {
        navigateToPlaylistDetail(0)

        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n * Poison \n * You shook me all night \n * Zombie \n * Rock'n Me \n * Thunderstruck \n * I Hate Myself for Loving You \n * Crazy \n * Knockin' on Heavens Door")
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylistDetails() {
        IdlingRegistry.getInstance()
            .unregister(idlingResource)
        Thread.sleep(3000)

        navigateToPlaylistDetail(0)
        assertDisplayed(R.id.playlist_detail_loader)
    }

    @Test
    fun hidesLoader() {
        navigateToPlaylistDetail(0)

        assertNotDisplayed(R.id.playlist_detail_loader)
    }

    private fun navigateToPlaylistDetail(childPosition: Int) {
        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlists_list_recycler), childPosition))))
            .perform(ViewActions.click())
    }
}