package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelComic
import io.reactivex.Observable

/**
 * Criado por rodrigo on 15/04/17.
 */
interface ApiData {
    fun getMarvelCharacters(offset: Int): Observable<ArrayList<MarvelCharacter>>
    fun getMarvelComics(): Observable<ArrayList<MarvelComic>>
    fun getDetailCharacter(id: Int): Observable<MarvelCharacter>
    fun getDetailComic(id: Int): Observable<MarvelComic>
}