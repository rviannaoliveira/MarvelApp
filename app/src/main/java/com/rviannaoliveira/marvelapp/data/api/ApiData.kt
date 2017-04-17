package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelComic
import io.reactivex.Observable

/**
 * Criado por rodrigo on 15/04/17.
 */
interface ApiData {
    fun getMarvelCharacters(): Observable<ArrayList<MarvelCharacter>>
    fun getMarvelComics(): Observable<ArrayList<MarvelComic>>
}