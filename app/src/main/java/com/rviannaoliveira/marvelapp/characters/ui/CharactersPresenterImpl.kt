package com.rviannaoliveira.marvelapp.characters.ui

import com.rviannaoliveira.marvelapp.data.IDataManager
import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Criado por rodrigo on 09/04/17.
 */
class CharactersPresenterImpl(private val view: CharactersView, private val dataManager: IDataManager) : CharactersPresenter {
    private val disposable = CompositeDisposable()

    override fun loadMarvelCharacters(offset: Int) {
        if (offset == 0) {
            view.showProgressBar()
        }

        val observableCharacters = dataManager.getMarvelCharacters(offset)

        disposable.add(observableCharacters.subscribe({ marvelCharacters ->
            view.loadCharacters(marvelCharacters)
            view.hideProgressBar()
        }, { error ->
            view.error()
            Timber.w(error)
        }))
    }

    override fun loadMarvelCharactersBeginLetter(letter: String) {
        view.showProgressBar()
        val observableCharacters = dataManager.getMarvelCharactersBeginLetter(letter)

        disposable.add(observableCharacters.subscribe({ marvelCharacters ->
            view.loadFilterCharacters(marvelCharacters)
            view.hideProgressBar()
        }, { error ->
            view.error()
            Timber.w(error)
        }))
    }

    override fun toggleFavorite(favorite: Favorite, checked: Boolean) {
        if (checked) {
            dataManager.insertFavorite(favorite)
        } else {
            dataManager.run { deleteFavorite(favorite, true).subscribe() }
        }
    }


    override fun onDisposable() {
        disposable.clear()
    }
}