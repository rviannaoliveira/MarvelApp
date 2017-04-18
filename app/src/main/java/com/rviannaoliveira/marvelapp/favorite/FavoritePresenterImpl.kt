package com.rviannaoliveira.marvelapp.comics

import android.util.Log
import com.rviannaoliveira.marvelapp.data.DataManager
import com.rviannaoliveira.marvelapp.favorite.FavoritePresenter
import com.rviannaoliveira.marvelapp.favorite.FavoriteView
import com.rviannaoliveira.marvelapp.model.Favorite

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
            Log.e("error", error.message)
        })
    }

    override fun deleteFavorite(favorite: Favorite) {
        DataManager.deleteFavorite(favorite)
    }



}