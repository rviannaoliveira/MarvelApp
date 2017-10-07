package com.rviannaoliveira.marvelapp.favorite

import com.rviannaoliveira.marvelapp.data.DataManager
import com.rviannaoliveira.marvelapp.data.DataManagerFactory
import com.rviannaoliveira.marvelapp.model.Favorite
import timber.log.Timber

/**
 * Criado por rodrigo on 14/04/17.
 */
class FavoritePresenterImpl(private val view: FavoriteView) : FavoritePresenter {
    private var dataManager: DataManager? = DataManagerFactory.getDefaultInstance()

    override fun loadFavorites() {
        view.showProgressBar()
        val observableFavorites = dataManager?.getAllFavorites()
        observableFavorites?.subscribe({ favorites ->
            view.loadFavorite(favorites)
            view.hideProgressBar()
        }, { error ->
            view.hideProgressBar()
            view.error()
            Timber.w(error.message)
        })
    }

    override fun deleteFavorite(favorite: Favorite, removeCharacter: Boolean) {
        dataManager?.deleteFavorite(favorite, removeCharacter)
    }



}