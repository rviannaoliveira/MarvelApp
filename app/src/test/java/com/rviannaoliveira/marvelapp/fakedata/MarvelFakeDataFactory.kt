package com.rviannaoliveira.marvelapp.fakedata

import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelCharacterDataContainer
import com.rviannaoliveira.marvelapp.model.MarvelCharacterDataWrapper

/**
 * Criado por rodrigo on 17/09/17.
 */
object MarvelFakeDataFactory {

    val fakeMarvelCharacterDataWrapperId2 by lazy {
        val marvelCharacterDataWrapper = MarvelCharacterDataWrapper()
        marvelCharacterDataWrapper.data = getMarvelCharacterDataContainer()
        marvelCharacterDataWrapper.data?.results = arrayListOf(MarvelCharacter(2))
        marvelCharacterDataWrapper
    }

    private fun getMarvelCharacterDataContainer(): MarvelCharacterDataContainer {
        return MarvelCharacterDataContainer()
    }

}