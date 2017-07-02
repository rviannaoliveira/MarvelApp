package com.rviannaoliveira.marvelapp.characters

import android.os.SystemClock.sleep
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.TestUtil
import org.hamcrest.Matchers.*

/**
 * Criado por rodrigo on 01/07/17.
 */
class RoboCharacters {
    fun clickSearchButton(): RoboCharacters {
        sleep(3000)
        onView(withId(R.id.menu_search)).perform(click())
        return this
    }

    fun findCharacters(characterName: String): RoboCharacters {
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText(characterName), closeSoftKeyboard())
        return this
    }

    fun checkCharacterScreen(characterName: String): RoboCharacters {
        sleep(1500)
        onView(withText(containsString(characterName))).check(matches(isDisplayed()))
        return this
    }

    fun closeSearchButton(): RoboCharacters {
        onView(withId(R.id.search_close_btn)).perform(click(), click())
        return this
    }

    fun checkCharacterScreenDoesntExist(characterName: String): RoboCharacters {
        onView(withText(containsString(characterName))).check(matches(not(isDisplayed())))
        return this
    }

    fun clickFilterButton(): RoboCharacters {
        sleep(3000)
        onView(allOf(withId(R.id.menu_filter), withContentDescription("Filter"), isDisplayed()))
                .perform(click())
        return this
    }

    fun chooseLetterFilter(letter: String): RoboCharacters {
        onView(allOf(withId(android.R.id.text1), withText(letter))).perform(click())
        return this
    }

    fun clickButtonOkDialogFilter(): RoboCharacters {
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(scrollTo(), click())
        sleep(3000)
        return this
    }

    fun clickItem(position: Int): RoboCharacters {
        onView(withId(R.id.list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CharactersAdapter.CharactersViewHolder>(position, click()))
        return this
    }

    fun backToList(): RoboCharacters {
        sleep(3000)
        onView(withContentDescription("Navigate up")).perform(click())
        return this
    }

    fun clickOnFavoriteItem(position: Int): RoboCharacters {
        sleep(3000)
        onView(withId(R.id.list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CharactersAdapter.CharactersViewHolder>(position,
                        TestUtil.clickChildViewWithId(R.id.check_favorite)))
        return this
    }

    fun clickOffFavoriteItem(position: Int): RoboCharacters {
        sleep(3000)
        onView(withId(R.id.list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CharactersAdapter.CharactersViewHolder>(position,
                        TestUtil.clickChildViewWithId(R.id.check_favorite)))
        return this
    }
}