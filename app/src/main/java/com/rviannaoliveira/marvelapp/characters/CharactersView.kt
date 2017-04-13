package com.rviannaoliveira.marvelapp.characters

import com.rviannaoliveira.marvelapp.model.MarvelCharacter

/**
 * Criado por rodrigo on 09/04/17.
 */
interface CharactersView {
    fun showProgressBar()
    fun hideProgressBar()
    fun loadCharacters(marvelCharacters: ArrayList<MarvelCharacter>)
}