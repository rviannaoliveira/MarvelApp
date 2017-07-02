package com.rviannaoliveira.marvelapp.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.rviannaoliveira.marvelapp.R

/**
 * Criado por rodrigo on 01/07/17.
 */
class RoboMain {

    fun clickFavorite(): RoboMain {
        onView(withId(R.id.action_favorite)).perform(click())
        return this
    }

    fun clickComic(): RoboMain {
        onView(withId(R.id.action_comic)).perform(click())
        return this
    }

    fun clickCharacters(): RoboMain {
        onView(withId(R.id.action_character)).perform(click())
        return this
    }

    fun existSearchButton(): RoboMain {
        onView(withId(R.id.menu_search)).check(matches(isDisplayed()))
        return this
    }

    fun existFilterButton(): RoboMain {
        onView(withId(R.id.menu_filter)).check(matches(isDisplayed()))
        return this
    }

    fun notExistSearchButton(): RoboMain {
        onView(withId(R.id.menu_search)).check(doesNotExist())
        return this
    }

    fun notExistFilterButton(): RoboMain {
        onView(withId(R.id.menu_filter)).check(doesNotExist())
        return this
    }


}