package com.rviannaoliveira.marvelapp

import com.nhaarman.mockito_kotlin.whenever
import com.rviannaoliveira.marvelapp.data.api.ApiData
import com.rviannaoliveira.marvelapp.data.api.MarvelApiHelper
import com.rviannaoliveira.marvelapp.data.api.MarvelService
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelCharacterDataContainer
import com.rviannaoliveira.marvelapp.model.MarvelCharacterDataWrapper
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
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

    @Mock private
    lateinit var marvelService: MarvelService


    @Before
    @Throws(Exception::class)
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun getMarvelCharactersCache() {
        val marvelApiHelper = getApiHelper()
        MarvelApiHelper.charactersCache = arrayListOf(MarvelCharacter(1))
        marvelApiHelper.getMarvelCharacters(0).test().assertValue(MarvelApiHelper.charactersCache)
    }

    //Perguntar pq no gradle quebra
    @Test
    fun getMarvelCharacters() {
        val marvelApiHelper = getApiHelper()
        val marvelCharacterDataWrapper = getMarvelCharacterDataWrapper()
        whenever(marvelService.getCharacters(MarvelApiHelper.LIMIT_REGISTER, 0)).thenReturn(Flowable.just(marvelCharacterDataWrapper))
        marvelApiHelper.getMarvelCharacters(0)
                .test()
                .assertComplete()
                .assertValue(marvelCharacterDataWrapper.data?.results)
    }

    private fun getMarvelCharacterDataContainer(): MarvelCharacterDataContainer {
        return MarvelCharacterDataContainer()
    }

    private fun getMarvelCharacterDataWrapper(): MarvelCharacterDataWrapper {
        val marvelCharacterDataWrapper = MarvelCharacterDataWrapper()
        marvelCharacterDataWrapper.data = getMarvelCharacterDataContainer()
        marvelCharacterDataWrapper.data?.results = arrayListOf(MarvelCharacter(2))
        return marvelCharacterDataWrapper
    }

    private fun getApiHelper(): ApiData {
        return MarvelApiHelper(marvelService)
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }

}