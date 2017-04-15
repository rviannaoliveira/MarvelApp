package com.rviannaoliveira.marvelapp.comics

import android.util.Log
import com.rviannaoliveira.marvelapp.data.DataManager

/**
 * Criado por rodrigo on 14/04/17.
 */
class ComicsPresenterImpl(private val view: ComicsView) : ComicsPresenter {
    private val dataManager = DataManager()

    override fun getMarvelComics() {
        view.showProgressBar()
        val observableComics = dataManager.getMarvelComics()
        observableComics.subscribe({ marvelComics ->
            view.loadComics(marvelComics)
            view.hideProgressBar()
        }, { error ->
            Log.e("error", error.message)
        })
    }


}