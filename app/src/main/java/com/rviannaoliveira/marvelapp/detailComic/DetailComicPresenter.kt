package com.rviannaoliveira.marvelapp.detailComic

/**
 * Criado por rodrigo on 09/04/17.
 */
interface DetailComicPresenter {
    fun getMarvelComic(id: Int)
    fun onDisposable()
}