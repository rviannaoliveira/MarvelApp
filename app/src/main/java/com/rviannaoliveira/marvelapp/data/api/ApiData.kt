package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelComic
import io.reactivex.Flowable

/**
 * Criado por rodrigo on 15/04/17.
 */
interface ApiData {
    fun getMarvelCharacters(offset: Int): Flowable<List<MarvelCharacter>>
    fun getMarvelComics(offset: Int): Flowable<List<MarvelComic>>
    fun getDetailCharacter(id: Int?): Flowable<MarvelCharacter>
    fun getDetailComic(id: Int?): Flowable<MarvelComic>
    fun removeFavoriteCharacter(idMarvel: Int?)
    fun removeFavoriteComic(idMarvel: Int?)
    fun getMarvelCharactersBeginLetter(letter: String): Flowable<List<MarvelCharacter>>
    fun getMarvelComicsBeginLetter(letter: String): Flowable<List<MarvelComic>>
}