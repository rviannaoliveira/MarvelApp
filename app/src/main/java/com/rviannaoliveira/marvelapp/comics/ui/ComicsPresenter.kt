package com.rviannaoliveira.marvelapp.comics.ui

import com.rviannaoliveira.marvelapp.base.BasePresenterFavorite

/**
 * Criado por rodrigo on 14/04/17.
 */
interface ComicsPresenter : BasePresenterFavorite{
    fun loadMarvelComics(offset: Int)
    fun loadMarvelComicsBeginLetter(letter: String)
    fun onDisposable()
}