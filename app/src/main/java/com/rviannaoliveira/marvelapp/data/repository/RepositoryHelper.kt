package com.rviannaoliveira.marvelapp.data.repository

import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.FavoriteGroup
import io.reactivex.Observable
import io.realm.Realm

/**
 * Criado por rodrigo on 15/04/17.
 */
class RepositoryHelper : RepositoryData {
    private val realm = Realm.getDefaultInstance()

    override fun getAllFavorites(): Observable<List<Favorite>> {
        val favorites: List<Favorite> = realm.where(Favorite::class.java).findAll().sort("id")
        return Observable.fromArray(favorites)
    }

    override fun insertFavorite(favorite: Favorite) {
        realm.insertOrUpdate(favorite)
    }

    override fun deleteFavorite(favorite: Favorite) {
        favorite.deleteFromRealm()
    }

    override fun getCharactersFavorites(): Observable<List<Favorite>> {
        val favorites: List<Favorite> = realm.where(Favorite::class.java)
                .equalTo("group", FavoriteGroup.CHARACTERS).findAll()
        return Observable.fromArray(favorites)
    }

}