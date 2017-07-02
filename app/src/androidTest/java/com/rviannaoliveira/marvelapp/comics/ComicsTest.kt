package com.rviannaoliveira.marvelapp.comics

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.rviannaoliveira.marvelapp.characters.RoboComic
import com.rviannaoliveira.marvelapp.initMockServer
import com.rviannaoliveira.marvelapp.main.MainActivity
import com.rviannaoliveira.marvelapp.readFileFromAssets
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * Criado por rodrigo on 01/07/17.
 */
@RunWith(AndroidJUnit4::class)
class ComicsTest {
    @Rule @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)
    private lateinit var robo: RoboComic
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        server.initMockServer()
        robo = RoboComic()
        activityRule.launchActivity(Intent())
    }

    @Test
    @Throws(Exception::class)
    fun search_for_civilwar_comic() {
        server.enqueue(MockResponse().setBody(server.readFileFromAssets(activityRule.activity, "comics.json")))

        robo.clickComicMenu()
                .clickSearchButton()
                .findComics("Cap Transport")
                .checkComicScreen("Cap Transport (2005) #18")
                .closeSearchButton()
    }

    @Test
    @Throws(Exception::class)
    fun filter_for_letter_d() {
        server.enqueue(MockResponse().setBody(server.readFileFromAssets(activityRule.activity, "comics.json")))
        server.enqueue(MockResponse().setBody(server.readFileFromAssets(activityRule.activity, "d_comics.json")))

        robo.clickComicMenu()
                .clickFilterButton()
                .chooseLetterFilter("D")
                .clickButtonOkDialogFilter()
                .checkComicScreen("Deadpool:")
    }

    @Test
    @Throws(Exception::class)
    fun filter_for_all() {
        server.enqueue(MockResponse().setBody(server.readFileFromAssets(activityRule.activity, "comics.json")))

        robo.clickComicMenu()
                .clickFilterButton()
                .chooseLetterFilter("ALL")
                .clickButtonOkDialogFilter()
                .checkComicScreen("Magician:")
    }

    @Test
    @Throws(Exception::class)
    fun click_item_list_and_back() {
        server.enqueue(MockResponse().setBody(server.readFileFromAssets(activityRule.activity, "comics.json")))
        server.enqueue(MockResponse().setBody(server.readFileFromAssets(activityRule.activity, "civil.json")))

        robo.clickComicMenu()
                .clickItem(2)
                .backToList()
    }

    @Test
    @Throws(Exception::class)
    fun click_favorite() {
        server.enqueue(MockResponse().setBody(server.readFileFromAssets(activityRule.activity, "comics.json")))

        robo.clickComicMenu()
                .clickOnFavoriteItem(2)
                .clickOffFavoriteItem(2)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        server.shutdown()
    }

}