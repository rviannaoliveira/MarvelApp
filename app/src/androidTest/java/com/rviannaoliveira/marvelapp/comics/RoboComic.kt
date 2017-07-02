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
class RoboComic {

    fun clickSearchButton(): RoboComic {
        sleep(3000)
        onView(withId(R.id.menu_search)).perform(click())
        return this
    }

    fun findComics(characterName: String): RoboComic {
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText(characterName), closeSoftKeyboard())
        return this
    }

    fun checkComicScreen(characterName: String): RoboComic {
        sleep(1500)
        onView(withText(containsString(characterName))).check(matches(isDisplayed()))
        return this
    }

    fun closeSearchButton(): RoboComic {
        onView(withId(R.id.search_close_btn)).perform(click(), click())
        return this
    }

    fun checkComicScreenDoesntExist(characterName: String): RoboComic {
        onView(withText(containsString(characterName))).check(matches(not(isDisplayed())))
        return this
    }

    fun clickFilterButton(): RoboComic {
        sleep(3000)
        onView(allOf(withId(R.id.menu_filter), withContentDescription("Filter"), isDisplayed()))
                .perform(click())
        return this
    }

    fun chooseLetterFilter(letter: String): RoboComic {
        onView(allOf(withId(android.R.id.text1), withText(letter))).perform(click())
        return this
    }

    fun clickButtonOkDialogFilter(): RoboComic {
        onView(allOf(withId(android.R.id.button1), withText("OK"))).perform(scrollTo(), click())
        sleep(3000)
        return this
    }

    fun clickItem(position: Int): RoboComic {
        onView(withId(R.id.list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CharactersAdapter.CharactersViewHolder>(position, click()))
        return this
    }

    fun backToList(): RoboComic {
        onView(withContentDescription("Navigate up")).perform(click())
        return this
    }

    fun clickOnFavoriteItem(position: Int): RoboComic {
        sleep(3000)
        onView(withId(R.id.list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CharactersAdapter.CharactersViewHolder>(position,
                        TestUtil.clickChildViewWithId(R.id.check_favorite)))
        return this
    }

    fun clickOffFavoriteItem(position: Int): RoboComic {
        sleep(3000)
        onView(withId(R.id.list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CharactersAdapter.CharactersViewHolder>(position,
                        TestUtil.clickChildViewWithId(R.id.check_favorite)))
        return this
    }

    fun clickComicMenu(): RoboComic {
        onView(withId(R.id.action_comic)).perform(click())
        return this
    }
}