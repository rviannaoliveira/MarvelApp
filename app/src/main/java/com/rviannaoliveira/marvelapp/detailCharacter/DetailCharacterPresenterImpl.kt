package com.rviannaoliveira.marvelapp.detailCharacter

import com.rviannaoliveira.marvelapp.characters.DetailCharacterPresenter
import com.rviannaoliveira.marvelapp.data.DataManagerFactory
import timber.log.Timber

/**
 * Criado por rodrigo on 21/04/17.
 */
class DetailCharacterPresenterImpl(private val view: DetailCharacterView) : DetailCharacterPresenter {

    override fun getMarvelCharacter(id: Int) {
        view.showProgressBar()
        DataManagerFactory.getDefaultInstance()?.loadDetailMarvelCharacter(id)
                ?.subscribe({ character ->
                    view.hideProgressBar()
                    view.loadCharacter(character)
                }, { error ->
                    view.hideProgressBar()
                    view.error()
                    Timber.e("error", error.message)
                })

    }
}