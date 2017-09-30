package com.rviannaoliveira.marvelapp.data

import android.support.annotation.VisibleForTesting
import com.rviannaoliveira.marvelapp.data.api.ApiData
import com.rviannaoliveira.marvelapp.data.api.MarvelApiHelper
import com.rviannaoliveira.marvelapp.data.repository.RepositoryData
import com.rviannaoliveira.marvelapp.data.repository.RepositoryHelper
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelComic
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Criado por rodrigo on 09/04/17.
 */
object DataManager {
    @VisibleForTesting
    private var apiData: ApiData = MarvelApiHelper()
    @VisibleForTesting
    private var repositoryData: RepositoryData = RepositoryHelper()

    fun getMarvelCharacters(offset: Int): Observable<ArrayList<MarvelCharacter>> {
        val characters = apiData.getMarvelCharacters(offset)
        val favorites = repositoryData.getCharactersFavorites()
        return Observable.zip(characters, favorites, BiFunction<ArrayList<MarvelCharacter>, List<Favorite>,
                ArrayList<MarvelCharacter>>(this::combineCharacter))

    }

    fun getMarvelCharactersBeginLetter(letter: String): Observable<ArrayList<MarvelCharacter>> {
        val characters = apiData.getMarvelCharactersBeginLetter(letter)
        val favorites = repositoryData.getCharactersFavorites()
        return Observable.zip(characters, favorites, BiFunction<ArrayList<MarvelCharacter>, List<Favorite>,
                ArrayList<MarvelCharacter>>(this::combineCharacter))

    }

    fun getMarvelComics(offset: Int): Observable<ArrayList<MarvelComic>> {
        val comics = apiData.getMarvelComics(offset)
        val favorites = repositoryData.getComicsFavorites()
        return Observable.zip(comics, favorites, BiFunction<ArrayList<MarvelComic>, List<Favorite>,
                ArrayList<MarvelComic>>(this::combineComic))
    }

    fun getMarvelComicsBeginLetter(letter: String): Observable<ArrayList<MarvelComic>> {
        val comics = apiData.getMarvelComicsBeginLetter(letter)
        val favorites = repositoryData.getComicsFavorites()
        return Observable.zip(comics, favorites, BiFunction<ArrayList<MarvelComic>, List<Favorite>,
                ArrayList<MarvelComic>>(this::combineComic))
    }

    fun getDetailMarvelCharacter(id: Int?): Observable<MarvelCharacter> {
        return apiData.getDetailCharacter(id)
    }

    fun getDetailComicCharacter(id: Int?): Observable<MarvelComic> {
        return apiData.getDetailComic(id)
    }

    fun getAllFavorites(): Observable<Favorite> {
        return repositoryData.getAllFavorites()
    }

    fun insertFavorite(favorite: Favorite) {
        repositoryData.insertFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        repositoryData.deleteFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite, removeCharacter: Boolean) {
        if (removeCharacter) apiData.removeFavoriteCharacter(favorite.idMarvel) else apiData.removeFavoriteComic(favorite.idMarvel)
        repositoryData.deleteFavorite(favorite)
    }

    private fun combineCharacter(characters: ArrayList<MarvelCharacter>, favorites: List<Favorite>): ArrayList<MarvelCharacter> {
        if (favorites.isEmpty()) {
            return characters
        }

        val mapFavorite = HashMap<Int?, Favorite>()

        for (favorite in favorites) {
            mapFavorite.put(favorite.idMarvel, favorite)
        }


        for (character in characters) {
            character.favorite = mapFavorite[character.id]
        }
        return characters
    }

    private fun combineComic(comics: ArrayList<MarvelComic>, favorites: List<Favorite>): ArrayList<MarvelComic> {
        if (favorites.isEmpty()) {
            return comics
        }

        val mapFavorite = HashMap<Int?, Favorite>()

        for (favorite in favorites) {
            mapFavorite.put(favorite.idMarvel, favorite)
        }


        for (comic in comics) {
            comic.favorite = mapFavorite[comic.id]
        }

        return comics
    }

}