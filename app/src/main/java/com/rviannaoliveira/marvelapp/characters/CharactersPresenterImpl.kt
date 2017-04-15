package com.rviannaoliveira.marvelapp.characters

import android.util.Log
import com.rviannaoliveira.marvelapp.data.DataManager

/**
 * Criado por rodrigo on 09/04/17.
 */
class CharactersPresenterImpl(private val charactersView: CharactersView) : CharactersPresenter {
    private val dataManager = DataManager()

    override fun getMarvelCharacters() {
        charactersView.showProgressBar()
        val observableCharacters = dataManager.getMarvelCharacters()
        observableCharacters.subscribe({ marvelCharacters ->
            charactersView.loadCharacters(marvelCharacters)
            charactersView.hideProgressBar()
        }, { error ->
            Log.e("error", error.message)
        })
    }

}