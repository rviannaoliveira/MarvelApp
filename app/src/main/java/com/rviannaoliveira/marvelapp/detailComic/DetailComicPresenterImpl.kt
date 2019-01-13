package com.rviannaoliveira.marvelapp.detailComic

import com.rviannaoliveira.marvelapp.data.IDataManager
import com.rviannaoliveira.marvelapp.detailCharacter.DetailComicView
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Criado por rodrigo on 21/04/17.
 */
class DetailComicPresenterImpl(private val view: DetailComicView, private val dataManager: IDataManager) : DetailComicPresenter {

    private val diposable = CompositeDisposable()

    override fun getMarvelComic(id: Int) {
        view.showProgressBar()
        diposable.add(dataManager.loadDetailComicCharacter(id)
                .subscribe({ comic ->
                    view.hideProgressBar()
                    view.loadComic(comic)
                }, { error ->
                    view.error()
                    Timber.e("error", error.message)
                }))

    }

    override fun onDisposable() {
        diposable.clear()
    }
}