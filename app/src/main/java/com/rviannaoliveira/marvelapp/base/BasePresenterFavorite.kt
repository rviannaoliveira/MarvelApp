package com.rviannaoliveira.marvelapp.base

import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.Observable

/**
 * Criado por rodrigo on 18/04/17.
 */
interface BasePresenterFavorite {
    fun getFavorites(): Observable<List<Favorite>>
    fun toggleFavorite(favorite: Favorite, checked: Boolean)
}