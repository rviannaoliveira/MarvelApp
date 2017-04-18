package com.rviannaoliveira.marvelapp.data

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
    private val apiData: ApiData = MarvelApiHelper()
    private val repositoryData: RepositoryData = RepositoryHelper()

    fun getMarvelCharacters(): Observable<ArrayList<MarvelCharacter>> {
        val characters = apiData.getMarvelCharacters()
        val favorites = repositoryData.getCharactersFavorites()
        return Observable.zip(characters, favorites, BiFunction<ArrayList<MarvelCharacter>, List<Favorite>, ArrayList<MarvelCharacter>>({ characters, favorites ->
            combineCharacter(characters, favorites)
        }))

    }

    fun getMarvelComics(): Observable<ArrayList<MarvelComic>> {
        val comics = apiData.getMarvelComics()
        val favorites = repositoryData.getCharactersFavorites()
        return Observable.zip(comics, favorites, BiFunction<ArrayList<MarvelComic>, List<Favorite>, ArrayList<MarvelComic>>({ comics, favorites ->
            combineComic(comics, favorites)
        }))
    }

    fun getAllFavorites(): Observable<List<Favorite>> {
        return repositoryData.getAllFavorites()
    }

    fun insertFavorite(favorite: Favorite) {
        repositoryData.insertFavorite(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
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