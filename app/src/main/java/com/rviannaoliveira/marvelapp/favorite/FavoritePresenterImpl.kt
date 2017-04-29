package com.rviannaoliveira.marvelapp.comics

import com.rviannaoliveira.marvelapp.data.DataManager
import com.rviannaoliveira.marvelapp.favorite.FavoritePresenter
import com.rviannaoliveira.marvelapp.favorite.FavoriteView
import com.rviannaoliveira.marvelapp.model.Favorite
import timber.log.Timber

/**
 * Criado por rodrigo on 14/04/17.
 */
class FavoritePresenterImpl(private val view: FavoriteView) : FavoritePresenter {

    override fun getFavorites() {
        view.showProgressBar()
        val observableFavorites = DataManager.getAllFavorites()
        observableFavorites.subscribe({ favorites ->
            view.loadFavorite(favorites)
            view.hideProgressBar()
        }, { error ->
            view.hideProgressBar()
            view.error()
            Timber.w(error.message)
        })
    }

    override fun deleteFavorite(favorite: Favorite) {
        DataManager.deleteFavorite(favorite)
    }



}