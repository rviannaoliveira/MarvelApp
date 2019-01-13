package com.rviannaoliveira.marvelapp.characters.ui

import com.rviannaoliveira.marvelapp.base.BasePresenterFavorite

/**
 * Criado por rodrigo on 09/04/17.
 */
interface CharactersPresenter : BasePresenterFavorite {
    fun loadMarvelCharacters(offset: Int)
    fun loadMarvelCharactersBeginLetter(letter: String)
    fun onDisposable()
}