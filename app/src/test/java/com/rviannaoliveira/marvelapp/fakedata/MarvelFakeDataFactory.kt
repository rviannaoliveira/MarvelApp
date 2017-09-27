package com.rviannaoliveira.marvelapp.fakedata

import com.rviannaoliveira.marvelapp.model.MarvelCharacter

/**
 * Criado por rodrigo on 17/09/17.
 */
object MarvelFakeDataFactory {


    val fakeMarvelCharacterTest by lazy {
        MarvelCharacter().apply {
            id = 1
        }
    }
}