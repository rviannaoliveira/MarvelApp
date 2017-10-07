package com.rviannaoliveira.marvelapp

import com.rviannaoliveira.marvelapp.fakedata.MarvelFakeDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class MarvelStateTest {

    @Test
    @Throws(Exception::class)
    fun test() {
        val marvelCharacter = MarvelFakeDataFactory.fakeMarvelCharacterTest

//        DataManager.getDetailMarvelCharacter(marvelCharacter.id)
//                .test()
//                .assertComplete()

    }
}