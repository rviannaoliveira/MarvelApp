package com.rviannaoliveira.marvelapp.characters

import com.rviannaoliveira.marvelapp.base.BaseView
import com.rviannaoliveira.marvelapp.model.MarvelCharacter

/**
 * Criado por rodrigo on 09/04/17.
 */
interface CharactersView : BaseView {
    fun loadCharacters(marvelCharacters: List<MarvelCharacter>)
    fun loadFilterCharacters(marvelCharacters: List<MarvelCharacter>)
}