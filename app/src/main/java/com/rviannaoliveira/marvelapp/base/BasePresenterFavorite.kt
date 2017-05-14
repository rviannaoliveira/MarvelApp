package com.rviannaoliveira.marvelapp.base

import com.rviannaoliveira.marvelapp.model.Favorite

/**
 * Criado por rodrigo on 18/04/17.
 */
interface BasePresenterFavorite {
    fun toggleFavorite(favorite: Favorite, checked: Boolean)
}