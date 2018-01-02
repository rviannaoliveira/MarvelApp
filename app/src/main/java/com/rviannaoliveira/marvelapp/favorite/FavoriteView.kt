package com.rviannaoliveira.marvelapp.favorite

import com.rviannaoliveira.marvelapp.base.BaseView
import com.rviannaoliveira.marvelapp.model.Favorite

/**
 * Criado por rodrigo on 15/04/17.
 */
interface FavoriteView : BaseView {
    fun loadFavorites(favorites: Favorite)
}