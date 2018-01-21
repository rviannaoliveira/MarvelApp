package com.rviannaoliveira.marvelapp.comics.ui

import com.rviannaoliveira.marvelapp.data.IDataManager
import com.rviannaoliveira.marvelapp.model.Favorite
import timber.log.Timber

/**
 * Criado por rodrigo on 14/04/17.
 */
class ComicsPresenterImpl(private val view: ComicsView, private val dataManager: IDataManager) : ComicsPresenter {

    override fun loadMarvelComics(offset: Int) {
        if (offset == 0) {
            view.showProgressBar()
        }
        val observableComics = dataManager.getMarvelComics(offset)
        observableComics.subscribe({ marvelComics ->
            view.loadComics(ArrayList(marvelComics.distinct()))
            view.hideProgressBar()
        }, { error ->
            view.hideProgressBar()
            view.error()
            Timber.w(error.message)
        })
    }

    override fun loadMarvelComicsBeginLetter(letter: String) {
        view.showProgressBar()
        val observableComics = dataManager.getMarvelComicsBeginLetter(letter)
        observableComics.subscribe({ marvelComics ->
            view.loadFilterComics(marvelComics)
            view.hideProgressBar()
        }, { error ->
            view.hideProgressBar()
            view.error()
            Timber.w(error.message)
        })
    }

    override fun toggleFavorite(favorite: Favorite, checked: Boolean) {
        if (checked) {
            dataManager.insertFavorite(favorite)
        } else {
            dataManager.deleteFavorite(favorite)
        }
    }


}