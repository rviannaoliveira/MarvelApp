package com.rviannaoliveira.marvelapp.data

import com.rviannaoliveira.marvelapp.data.api.ApiData
import com.rviannaoliveira.marvelapp.data.api.MarvelApiHelper
import com.rviannaoliveira.marvelapp.data.repository.RepositoryData
import com.rviannaoliveira.marvelapp.data.repository.RepositoryHelper
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.FavoriteGroup
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

    fun getMarvelCharacters(offset: Int): Observable<ArrayList<MarvelCharacter>> {
        val characters = apiData.getMarvelCharacters(offset)
        val favorites = repositoryData.getCharactersFavorites()
        return Observable.zip(characters, favorites, BiFunction<ArrayList<MarvelCharacter>, List<Favorite>, ArrayList<MarvelCharacter>>({ characters, favorites ->
            combineCharacter(characters, favorites)
        }))

    }

    fun getMarvelComics(offset: Int): Observable<ArrayList<MarvelComic>> {
        val comics = apiData.getMarvelComics(offset)
        val favorites = repositoryData.getComicsFavorites()
        return Observable.zip(comics, favorites, BiFunction<ArrayList<MarvelComic>, List<Favorite>, ArrayList<MarvelComic>>({ comics, favorites ->
            combineComic(comics, favorites)
        }))
    }

    fun getDetailMarvelCharacter(id: Int): Observable<MarvelCharacter> {
        return apiData.getDetailCharacter(id)
    }

    fun getDetailComicCharacter(id: Int): Observable<MarvelComic> {
        return apiData.getDetailComic(id)
    }

    fun getAllFavorites(): Observable<Favorite> {
        val favorite = Favorite()
        repositoryData.getAllFavorites()
                .flatMapIterable({ list -> list })
                .forEach({ item ->
                    if (FavoriteGroup.CHARACTERS == item.group) {
                        favorite.characters.add(item)
                    } else {
                        favorite.comics.add(item)
                    }
                })

        return Observable.just(favorite)
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