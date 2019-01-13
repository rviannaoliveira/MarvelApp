package com.rviannaoliveira.marvelapp.data

import com.rviannaoliveira.marvelapp.data.api.IApiData
import com.rviannaoliveira.marvelapp.data.repository.IRepositoryData
import com.rviannaoliveira.marvelapp.data.repository.KeyDatabase
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelComic
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * Criado por rodrigo on 09/04/17.
 */
class DataManager(private val apiData: IApiData,
                  private val repositoryData: IRepositoryData) : IDataManager {

    override fun getMarvelCharacters(offset: Int): Single<List<MarvelCharacter>> {
        val characters = apiData.getMarvelCharacters(offset)
        val favorites = repositoryData.getCharactersFavorites().firstOrError()
        return Single.zip(characters, favorites, BiFunction<List<MarvelCharacter>, List<Favorite>,
                List<MarvelCharacter>>(this::combineCharacter))
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMarvelCharactersBeginLetter(letter: String): Single<List<MarvelCharacter>> {
        val characters = apiData.getMarvelCharactersBeginLetter(letter)
        val favorites = repositoryData.getCharactersFavorites().firstOrError()
        return Single.zip(characters, favorites, BiFunction<List<MarvelCharacter>, List<Favorite>,
                List<MarvelCharacter>>(this::combineCharacter))

    }

    override fun getMarvelComics(offset: Int): Single<List<MarvelComic>> {
        val comics = apiData.getMarvelComics(offset)
        val favorites = repositoryData.getComicsFavorites().firstOrError()
        return Single.zip(comics, favorites, BiFunction<List<MarvelComic>, List<Favorite>,
                List<MarvelComic>>(this::combineComic))
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMarvelComicsBeginLetter(letter: String): Single<List<MarvelComic>> {
        val comics = apiData.getMarvelComicsBeginLetter(letter)
        val favorites = repositoryData.getComicsFavorites().firstOrError()
        return Single.zip(comics, favorites, BiFunction<List<MarvelComic>, List<Favorite>,
                List<MarvelComic>>(this::combineComic))
    }

    override fun loadDetailMarvelCharacter(id: Int?): Single<MarvelCharacter> {
        return apiData.getDetailCharacter(id)
    }

    override fun loadDetailComicCharacter(id: Int?): Single<MarvelComic> {
        return apiData.getDetailComic(id)
    }

    override fun getAllFavorites(): Single<Favorite> {
        val favorite = Favorite()

        return repositoryData.getAllFavorites()
                .firstOrError()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map { list ->
                    list.forEach { item ->
                        if (KeyDatabase.FavoriteGroup.CHARACTERS == item.groupType) {
                            favorite.characters.add(item)
                        } else {
                            favorite.comics.add(item)
                        }
                    }
                    favorite
                }
    }

    override fun insertFavorite(favorite: Favorite) {
        repositoryData.insertFavorite(favorite)
    }

    override fun deleteFavorite(favorite: Favorite, removeCharacter: Boolean): Single<Unit> {
        if (removeCharacter) apiData.removeFavoriteCharacterCache(favorite.idMarvel) else apiData.removeFavoriteComicCache(favorite.idMarvel)
        return repositoryData.deleteFavorite(favorite)
    }

    private fun combineCharacter(characters: List<MarvelCharacter>, favorites: List<Favorite>): List<MarvelCharacter> {
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

    private fun combineComic(comics: List<MarvelComic>, favorites: List<Favorite>): List<MarvelComic> {
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

interface IDataManager {
    fun getMarvelCharacters(offset: Int): Single<List<MarvelCharacter>>
    fun getMarvelCharactersBeginLetter(letter: String): Single<List<MarvelCharacter>>
    fun insertFavorite(favorite: Favorite)
    fun deleteFavorite(favorite: Favorite, removeCharacter: Boolean = false): Single<Unit>
    fun getAllFavorites(): Single<Favorite>
    fun loadDetailMarvelCharacter(id: Int?): Single<MarvelCharacter>
    fun loadDetailComicCharacter(id: Int?): Single<MarvelComic>
    fun getMarvelComics(offset: Int): Single<List<MarvelComic>>
    fun getMarvelComicsBeginLetter(letter: String): Single<List<MarvelComic>>
}
