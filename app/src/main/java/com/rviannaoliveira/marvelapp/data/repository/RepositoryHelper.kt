package com.rviannaoliveira.marvelapp.data.repository

import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.FavoriteGroup
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import io.reactivex.Observable
import io.realm.Realm

/**
 * Criado por rodrigo on 15/04/17.
 */
class RepositoryHelper : RepositoryData {
    private val realm = Realm.getDefaultInstance()

    override fun getAllFavorites(): Observable<Favorite> {
        val favorites: List<Favorite> = realm.where(Favorite::class.java).findAll()
        val favorite = Favorite()

        Observable.fromArray(favorites)
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

    override fun insertFavorite(favorite: Favorite) {
        if (favorite.id == null) {
            favorite.id = this.getNextKey()
        }
        realm.executeTransactionAsync { realm1 -> realm1.insertOrUpdate(favorite) }
    }

    override fun deleteFavorite(favorite: Favorite) {
        realm.beginTransaction()
        val favoriteDeleted = realm.where(Favorite::class.java).equalTo(MarvelConstant.ID, favorite.id).findFirst()
        favoriteDeleted.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun getCharactersFavorites(): Observable<List<Favorite>> {
        val favorites: List<Favorite> = realm.where(Favorite::class.java)
                .equalTo(MarvelConstant.GROUP, FavoriteGroup.CHARACTERS).findAll()
        return Observable.fromArray(favorites)
    }

    override fun getComicsFavorites(): Observable<List<Favorite>> {
        val favorites: List<Favorite> = realm.where(Favorite::class.java)
                .equalTo(MarvelConstant.GROUP, FavoriteGroup.COMICS).findAll()
        return Observable.fromArray(favorites)
    }

    private fun getNextKey(): Int {
        try {
            return realm.where(Favorite::class.java).max(MarvelConstant.ID).toInt() + 1
        } catch (e: Exception) {
            return 1
        }

    }
}