package com.rviannaoliveira.marvelapp.main

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainTest {
    @Rule @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)
    private lateinit var robo: RoboMain

    @Before
    fun setUp() {
        robo = RoboMain()
        activityRule.launchActivity(Intent())
    }

    @Test
    @Throws(Exception::class)
    fun navigation_inside_navigationBottomView() {
        robo.clickFavorite()
                .clickComic()
                .clickCharacters()
    }

    @Test
    @Throws(Exception::class)
    fun there_are_filter_and_search_button() {
        robo.existSearchButton()
                .existFilterButton()
                .clickComic()
                .existSearchButton()
                .existFilterButton()
                .clickFavorite()
                .notExistSearchButton()
                .notExistSearchButton()

    }
}
