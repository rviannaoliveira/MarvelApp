package com.rviannaoliveira.marvelapp.characters

import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import com.rviannaoliveira.marvelapp.R
import org.hamcrest.Matchers

/**
 * Criado por rodrigo on 28/01/18.
 */
fun filterCharacters(func: RoboCharactersFilter.() -> Unit) = RoboCharactersFilter().apply(func)

class RoboCharactersFilter {

    fun clickFilterButton(): RoboCharactersFilter {
        SystemClock.sleep(3000)
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.menu_filter), ViewMatchers.withContentDescription("Filter"), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        return this
    }

    fun chooseLetterFilter(letter: String): RoboCharactersFilter {
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(android.R.id.text1), ViewMatchers.withText(letter))).perform(ViewActions.click())
        return this
    }

    fun clickButtonOkDialogFilter(): RoboCharactersFilter {
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(android.R.id.button1), ViewMatchers.withText("OK"))).perform(ViewActions.scrollTo(), ViewActions.click())
        SystemClock.sleep(3000)
        return this
    }

}