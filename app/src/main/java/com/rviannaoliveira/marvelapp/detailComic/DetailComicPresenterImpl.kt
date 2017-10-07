package com.rviannaoliveira.marvelapp.detailComic

import com.rviannaoliveira.marvelapp.characters.DetailComicPresenter
import com.rviannaoliveira.marvelapp.data.DataManagerFactory
import com.rviannaoliveira.marvelapp.detailCharacter.DetailComicView
import timber.log.Timber

/**
 * Criado por rodrigo on 21/04/17.
 */
class DetailComicPresenterImpl(private val view: DetailComicView) : DetailComicPresenter {

    override fun getMarvelComic(id: Int) {
        view.showProgressBar()
        DataManagerFactory.getDefaultInstance()?.loadDetailComicCharacter(id)
                ?.subscribe({ comic ->
                    view.hideProgressBar()
                    view.loadComic(comic)
                }, { error ->
                    view.hideProgressBar()
                    view.error()
                    Timber.e("error", error.message)
                })

    }
}