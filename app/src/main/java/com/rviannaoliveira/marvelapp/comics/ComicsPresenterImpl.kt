package com.rviannaoliveira.marvelapp.comics

import com.rviannaoliveira.marvelapp.data.DataManager
import com.rviannaoliveira.marvelapp.model.Favorite
import timber.log.Timber

/**
 * Criado por rodrigo on 14/04/17.
 */
class ComicsPresenterImpl(private val view: ComicsView) : ComicsPresenter {

    override fun getMarvelComics(offset: Int) {
        view.showProgressBar()
        val observableComics = DataManager.getMarvelComics(offset)
        observableComics.subscribe({ marvelComics ->
            view.loadComics(marvelComics)
            view.hideProgressBar()
        }, { error ->
            view.hideProgressBar()
            view.error()
            Timber.w(error.message)
        })
    }

    override fun toggleFavorite(favorite: Favorite, checked: Boolean) {
        if (checked) {
            DataManager.insertFavorite(favorite)
        } else {
            DataManager.deleteFavorite(favorite)
        }
    }


}