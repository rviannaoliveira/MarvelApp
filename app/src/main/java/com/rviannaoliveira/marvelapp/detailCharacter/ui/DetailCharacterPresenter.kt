package com.rviannaoliveira.marvelapp.detailCharacter.ui

/**
 * Criado por rodrigo on 09/04/17.
 */
interface DetailCharacterPresenter {
    fun getMarvelCharacter(id: Int)
    fun onDisposable()
}