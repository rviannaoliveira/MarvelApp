package com.rviannaoliveira.marvelapp.characters

import com.rviannaoliveira.marvelapp.data.DataManagerInterface
import com.rviannaoliveira.marvelapp.model.Favorite
import timber.log.Timber

/**
 * Criado por rodrigo on 09/04/17.
 */
class CharactersPresenterImpl(private val charactersView: CharactersView, private val dataManager: DataManagerInterface?) : CharactersPresenter {

    override fun loadMarvelCharacters(offset: Int) {
        if (offset == 0) {
            charactersView.showProgressBar()
        }

        val observableCharacters = dataManager?.getMarvelCharacters(offset)

        observableCharacters?.subscribe({ marvelCharacters ->
            charactersView.loadCharacters(marvelCharacters)
        }, { error ->
            charactersView.error()
            Timber.w(error)
        }, {
            charactersView.hideProgressBar()
        })
    }

    override fun loadMarvelCharactersBeginLetter(letter: String) {
        charactersView.showProgressBar()
        val observableCharacters = dataManager?.getMarvelCharactersBeginLetter(letter)

        observableCharacters?.subscribe({ marvelCharacters ->
            charactersView.loadFilterCharacters(marvelCharacters)
        }, { error ->
            charactersView.error()
            Timber.w(error)
        }, { charactersView.hideProgressBar() })
    }

    override fun toggleFavorite(favorite: Favorite, checked: Boolean) {
        if (checked) {
            dataManager?.insertFavorite(favorite)
        } else {
            dataManager?.run { deleteFavorite(favorite, true).subscribe() }
        }
    }


}