package com.rviannaoliveira.marvelapp.data

import com.rviannaoliveira.marvelapp.data.api.MarvelApiHelper
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import io.reactivex.Observable

/**
 * Criado por rodrigo on 09/04/17.
 */
class DataManager {
    private val marvelApiHelper = MarvelApiHelper()

    fun getMarvelCharacters(): Observable<ArrayList<MarvelCharacter>?>? {
        return marvelApiHelper.getMarvelCharacters()
    }

}