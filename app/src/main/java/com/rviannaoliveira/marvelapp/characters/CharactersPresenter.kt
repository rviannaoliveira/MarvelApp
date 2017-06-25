package com.rviannaoliveira.marvelapp.characters

import com.rviannaoliveira.marvelapp.base.BasePresenterFavorite

/**
 * Criado por rodrigo on 09/04/17.
 */
interface CharactersPresenter : BasePresenterFavorite {
    fun getMarvelCharacters(offset: Int)
    fun getMarvelCharactersBeginLetter(letter: String)
}