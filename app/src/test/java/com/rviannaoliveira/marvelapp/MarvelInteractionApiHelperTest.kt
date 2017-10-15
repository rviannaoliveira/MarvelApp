package com.rviannaoliveira.marvelapp

import com.nhaarman.mockito_kotlin.whenever
import com.rviannaoliveira.marvelapp.data.api.ApiData
import com.rviannaoliveira.marvelapp.data.api.MarvelApiHelper
import com.rviannaoliveira.marvelapp.data.api.MarvelService
import com.rviannaoliveira.marvelapp.fakedata.MarvelFakeDataFactory
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import io.reactivex.Flowable
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config


/**
 * Criado por rodrigo on 08/10/17.
 */
@RunWith(MockitoJUnitRunner::class)
@Config(constants = BuildConfig::class)
class MarvelInteractionApiHelperTest {
    @get:Rule
    val mockito = MockitoJUnit.rule()

    @get:Rule
    val rxExternalResources = RxExternalResources()

    @Mock private
    lateinit var marvelService: MarvelService

    @Test
    fun getMarvelCharactersCache() {
        val marvelApiHelper = getApiHelper()
        MarvelApiHelper.charactersCache = arrayListOf(MarvelCharacter(1))
        marvelApiHelper.getMarvelCharacters(0)
                .test()
                .assertValue(MarvelApiHelper.charactersCache)
    }

    @Test
    fun getMarvelCharacters() {
        val marvelApiHelper = getApiHelper()
        val marvelCharacterDataWrapper = MarvelFakeDataFactory.fakeMarvelCharacterDataWrapperId2

        whenever(marvelService.getCharacters(MarvelApiHelper.LIMIT_REGISTER, 0)).thenReturn(Flowable.just(marvelCharacterDataWrapper))

        marvelApiHelper.getMarvelCharacters(0)
                .test()
                .assertComplete()
                .assertValue(marvelCharacterDataWrapper.data?.results)
    }

    private fun getApiHelper(): ApiData {
        return MarvelApiHelper(marvelService)
    }

    @After
    fun finish() {
        clearCache()
    }

    private fun clearCache() {
        MarvelApiHelper.charactersCache.clear()
        MarvelApiHelper.comicsCache.clear()
    }

}