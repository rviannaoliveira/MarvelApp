package com.rviannaoliveira.marvelapp

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.rviannaoliveira.marvelapp.main.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumentation test, which will execute on an Android device.

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class CharacterTest {
    @Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        val baseUrl = server.url("/v2/marvel/")
        server.start()
    }

    @Test
    @Throws(Exception::class)
    fun useAppContext() {
    }

    fun finish() {
        server.shutdown()
    }
}
