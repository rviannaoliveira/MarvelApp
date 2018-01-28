package com.rviannaoliveira.marvelapp.characters

import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import com.rviannaoliveira.marvelapp.R

/**
 * Criado por rodrigo on 28/01/18.
 */
fun searchCharacters(func: RoboCharactersSearch.() -> Unit) = RoboCharactersSearch().apply(func)

class RoboCharactersSearch {

    fun clickSearchButton(): RoboCharactersSearch {
        SystemClock.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.menu_search)).perform(ViewActions.click())
        return this
    }

    fun findCharacters(characterName: String): RoboCharactersSearch {
        Espresso.onView(ViewMatchers.withId(android.support.design.R.id.search_src_text)).perform(ViewActions.typeText(characterName), ViewActions.closeSoftKeyboard())
        return this
    }
}