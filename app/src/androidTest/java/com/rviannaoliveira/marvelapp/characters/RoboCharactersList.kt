package com.rviannaoliveira.marvelapp.characters

import android.os.SystemClock.sleep
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.TestUtil
import com.rviannaoliveira.marvelapp.characters.ui.CharactersAdapter
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.not

/**
 * Criado por rodrigo on 01/07/17.
 */

fun listCharacters(func: RoboCharacters.() -> Unit) = RoboCharacters().apply(func)

class RoboCharacters {


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