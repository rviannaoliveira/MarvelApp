package com.rviannaoliveira.marvelapp.characters

import com.rviannaoliveira.marvelapp.data.DataManager
import com.rviannaoliveira.marvelapp.model.Favorite
import timber.log.Timber

/**
 * Criado por rodrigo on 09/04/17.
 */
class CharactersPresenterImpl(private val charactersView: CharactersView) : CharactersPresenter {

    override fun getMarvelCharacters(offset: Int) {
        if (offset == 0) {
            charactersView.showProgressBar()
        }
        val observableCharacters = DataManager.getMarvelCharacters(offset)
        observableCharacters.subscribe({ marvelCharacters ->
            charactersView.loadCharacters(marvelCharacters)
            charactersView.hideProgressBar()
        }, { error ->
            charactersView.hideProgressBar()
            charactersView.error()
            Timber.w(error)
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