package com.rviannaoliveira.marvelapp.characters

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.rviannaoliveira.marvelapp.initMockServer
import com.rviannaoliveira.marvelapp.main.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Criado por rodrigo on 01/07/17.
 */
@RunWith(AndroidJUnit4::class)
class CharactersTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)
    private lateinit var robo: RoboCharacters
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        robo = RoboCharacters()
        server.initMockServer()
        activityRule.launchActivity(Intent())
    }

    @Test
    @Throws(Exception::class)
    fun search_for_abomination_character() {
        searchCharacters {
            clickSearchButton()
            findCharacters("Abom")
        }
        listCharacters {
            checkCharacterScreen("Abomination (Emil")
            closeSearchButton()
                    .checkCharacterScreenDoesntExist("Abomination (Emil")
        }
    }

    @Test
    @Throws(Exception::class)
    fun filter_for_letter_d() {
        filterCharacters {
            clickFilterButton()
            chooseLetterFilter("D")
            clickButtonOkDialogFilter()
        }

        listCharacters {
            checkCharacterScreen("Dagger")
        }
    }

    @Test
    @Throws(Exception::class)
    fun filter_for_all() {
        filterCharacters {
            clickFilterButton()
            chooseLetterFilter("ALL")
            clickButtonOkDialogFilter()
        }

        listCharacters {
            checkCharacterScreen("3-D Man")
        }
    }

    @Test
    @Throws(Exception::class)
    fun click_item_list_and_back() {
        listCharacters {
            clickItem(2)
            backToList()
        }
    }

    @Test
    @Throws(Exception::class)
    fun click_favorite() {
        listCharacters {
            clickOnFavoriteItem(2)
            clickOffFavoriteItem(2)
        }
    }
}