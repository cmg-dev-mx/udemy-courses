package mx.dev.shell.android.shellnotesapp.utils

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> =
    object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("position $childPosition of parent")
            parentMatcher.describeTo(description)
        }

        override fun matchesSafely(item: View): Boolean {
            if (item.parent !is ViewGroup) {
                return false
            }
            val parent = item.parent as ViewGroup

            return (parentMatcher.matches(parent)
                    && parent.childCount > childPosition
                    && parent.getChildAt(childPosition) == item)
        }
    }

fun ViewInteraction.getText(): String {
    var text = String()

    this.perform(object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Text of the view"
        }

        override fun perform(uiController: UiController?, view: View?) {
            val tv = view as TextView
            text = tv.text.toString()
        }
    })

    return text
}