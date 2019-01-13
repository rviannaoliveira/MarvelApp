package com.rviannaoliveira.marvelapp.detailCharacter.ui

import com.rviannaoliveira.marvelapp.data.IDataManager
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Criado por rodrigo on 21/04/17.
 */
class DetailCharacterPresenterImpl(private val view: DetailCharacterView,
                                   private val dataManager: IDataManager) : DetailCharacterPresenter {

    private val disposable = CompositeDisposable()

    override fun getMarvelCharacter(id: Int) {
        view.showProgressBar()
        disposable.add(dataManager.loadDetailMarvelCharacter(id)
                .subscribe({ character ->
                    view.hideProgressBar()
                    view.loadCharacter(character)
                }, { error ->
                    view.hideProgressBar()
                    view.error()
                    Timber.e("error", error.message)
                }))

    }

    override fun onDisposable() {
        disposable.clear()
    }
}