package com.rviannaoliveira.marvelapp

import com.nhaarman.mockito_kotlin.whenever
import com.rviannaoliveira.marvelapp.data.api.IApiData
import com.rviannaoliveira.marvelapp.data.api.MarvelApiHelper
import com.rviannaoliveira.marvelapp.data.api.MarvelService
import com.rviannaoliveira.marvelapp.fakedata.MarvelFakeDataFactory
import io.reactivex.Flowable
import junit.framework.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config


/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */

@RunWith(MockitoJUnitRunner::class)
@Config(constants = BuildConfig::class)
class MarvelStateTest {

    @get:Rule
    val mockito = MockitoJUnit.rule()

    @get:Rule
    val rxExternalResources = RxExternalResources()

    @Mock private
    lateinit var marvelService: MarvelService

    @Test
    fun getMarvelCharactersCache() {
        val marvelApiHelper = getApiHelper()

        whenever(marvelService.getCharacters(MarvelApiHelper.LIMIT_REGISTER, 0))
                .thenReturn(Flowable.just(MarvelFakeDataFactory.fakeMarvelCharacterDataWrapperId2))

        marvelApiHelper.getMarvelCharacters(0)
                .test()
                .assertComplete()

        Assert.assertSame(MarvelApiHelper.charactersCache[0], MarvelFakeDataFactory.fakeMarvelCharacterDataWrapperId2.data?.results!![0])

    }

    private fun getApiHelper(): IApiData {
        return MarvelApiHelper(marvelService)
    }
}