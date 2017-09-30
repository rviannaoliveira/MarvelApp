package com.rviannaoliveira.marvelapp.data.repository

import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Criado por rodrigo on 15/04/17.
 */
class RepositoryHelper : RepositoryData {
    private val favoriteDao = AppDatabaseFactory.getDefaultInstance().favoriteDao()

    override fun getAllFavorites(): Observable<Favorite> {
        val favorites: Flowable<List<Favorite>> = favoriteDao.getAll()
        val favorite = Favorite()

        favorites
                .flatMapIterable({ list -> list })
                .forEach({ item ->
                    if (KeyDatabase.FavoriteGroup.CHARACTERS == item.group) {
                        favorite.characters.add(item)
                    } else {
                        favorite.comics.add(item)
                    }
                })
        return Observable.just(favorite)
    }

    override fun insertFavorite(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    override fun deleteFavorite(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }

    override fun getCharactersFavorites(): Flowable<List<Favorite>> {
        return favoriteDao.getCharactersFavorites()
    }

    override fun getComicsFavorites(): Flowable<List<Favorite>> {
        return favoriteDao.getComicsFavorites()
    }
}