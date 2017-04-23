package com.rviannaoliveira.marvelapp.detailCharacter

import android.util.Log
import com.rviannaoliveira.marvelapp.characters.DetailCharacterPresenter
import com.rviannaoliveira.marvelapp.data.DataManager

/**
 * Criado por rodrigo on 21/04/17.
 */
class DetailCharacterPresenterImpl(private val view: DetailCharacterView) : DetailCharacterPresenter {

    override fun getMarvelCharacter(id: Int) {
        view.showProgressBar()
        DataManager.getDetailMarvelCharacter(id)
                .subscribe({ character ->
                    view.loadCharacter(character)
                    view.hideProgressBar()
                }, { error ->
                    Log.e("error", error.message)
                })

    }
}