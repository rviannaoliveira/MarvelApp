package com.rviannaoliveira.marvelapp.favorite

import com.rviannaoliveira.marvelapp.data.IDataManager
import com.rviannaoliveira.marvelapp.model.Favorite
import timber.log.Timber

/**
 * Criado por rodrigo on 14/04/17.
 */
class FavoritePresenterImpl(private var view: FavoriteView, private val dataManager: IDataManager) : FavoritePresenter {
    override fun loadFavorites() {
        view.let {
            it.showProgressBar()
            val observableFavorites = dataManager.getAllFavorites()
            observableFavorites.subscribe({ favorites ->
                it.loadFavorites(favorites)
                it.hideProgressBar()
            }, { error ->
                it.hideProgressBar()
                it.error()
                Timber.w(error.message)
            })
        }


    }

    override fun deleteFavorite(favorite: Favorite, removeCharacter: Boolean) {
        dataManager.run {
            deleteFavorite(favorite, removeCharacter).subscribe({ _ ->
                loadFavorites()
            })
        }
    }
}