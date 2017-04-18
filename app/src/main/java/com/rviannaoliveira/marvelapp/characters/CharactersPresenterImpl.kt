package com.rviannaoliveira.marvelapp.characters

import android.util.Log
import com.rviannaoliveira.marvelapp.data.DataManager
import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.Observable

/**
 * Criado por rodrigo on 09/04/17.
 */
class CharactersPresenterImpl(private val charactersView: CharactersView) : CharactersPresenter {

    override fun getMarvelCharacters() {
        charactersView.showProgressBar()
        val observableCharacters = DataManager.getMarvelCharacters()
        observableCharacters.subscribe({ marvelCharacters ->
            charactersView.loadCharacters(marvelCharacters)
            charactersView.hideProgressBar()
        }, { error ->
            Log.e("error", error.message)
        })
    }

    override fun getFavorites(): Observable<List<Favorite>> {
        return DataManager.getAllFavorites()
    }

    override fun toggleFavorite(favorite: Favorite, checked: Boolean) {
        if (checked) {
            DataManager.insertFavorite(favorite)
        } else {
            DataManager.deleteFavorite(favorite)
        }
    }




}