package com.rviannaoliveira.marvelapp.favorite

import com.rviannaoliveira.marvelapp.model.Favorite

/**
 * Criado por rodrigo on 15/04/17.
 */
interface FavoritePresenter {
    fun getFavorites()
    fun deleteFavorite(favorite: Favorite, b: Boolean)

}