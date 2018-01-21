package com.rviannaoliveira.marvelapp.characters.ui

import com.rviannaoliveira.marvelapp.data.IDataManager
import com.rviannaoliveira.marvelapp.model.Favorite
import timber.log.Timber

/**
 * Criado por rodrigo on 09/04/17.
 */
class CharactersPresenterImpl(private val view: CharactersView, private val dataManager: IDataManager) : CharactersPresenter {

    override fun loadMarvelCharacters(offset: Int) {
        if (offset == 0) {
            view.showProgressBar()
        }

        val observableCharacters = dataManager.getMarvelCharacters(offset)

        observableCharacters.subscribe({ marvelCharacters ->
            view.loadCharacters(marvelCharacters)
        }, { error ->
            view.error()
            Timber.w(error)
        }, {
            view.hideProgressBar()
        })
    }

    override fun loadMarvelCharactersBeginLetter(letter: String) {
        view.showProgressBar()
        val observableCharacters = dataManager.getMarvelCharactersBeginLetter(letter)

        observableCharacters.subscribe({ marvelCharacters ->
            view.loadFilterCharacters(marvelCharacters)
        }, { error ->
            view.error()
            Timber.w(error)
        }, { view.hideProgressBar() })
    }

    override fun toggleFavorite(favorite: Favorite, checked: Boolean) {
        if (checked) {
            dataManager.insertFavorite(favorite)
        } else {
            dataManager.run { deleteFavorite(favorite, true).subscribe() }
        }
    }


}