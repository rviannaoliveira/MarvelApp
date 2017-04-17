package com.rviannaoliveira.marvelapp.characters

import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.Observable

/**
 * Criado por rodrigo on 09/04/17.
 */
interface CharactersPresenter {
    fun getMarvelCharacters()
    fun getCharactersFavorites(): Observable<List<Favorite>>
    fun toggleFavorite(favorite: Favorite, checked: Boolean)
}