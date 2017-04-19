package com.rviannaoliveira.marvelapp.data.repository

import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.FavoriteGroup
import io.reactivex.Observable
import io.realm.Realm

/**
 * Criado por rodrigo on 15/04/17.
 */
class RepositoryHelper : RepositoryData {
    private val ID = "id"
    private val realm = Realm.getDefaultInstance()

    override fun getAllFavorites(): Observable<List<Favorite>> {
        val favorites: List<Favorite> = realm.where(Favorite::class.java).findAll().sort(ID)
        return Observable.fromArray(favorites)
    }

    override fun insertFavorite(favorite: Favorite) {
        if (favorite.id == null) {
            favorite.id = this.getNextKey()
        }
        realm.executeTransactionAsync { realm1 -> realm1.insertOrUpdate(favorite) }
    }

    override fun deleteFavorite(favorite: Favorite) {
        realm.beginTransaction()
        val favoriteDeleted = realm.where(Favorite::class.java).equalTo(ID, favorite.id).findFirst()
        favoriteDeleted.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun getCharactersFavorites(): Observable<List<Favorite>> {
        val favorites: List<Favorite> = realm.where(Favorite::class.java)
                .equalTo("group", FavoriteGroup.CHARACTERS).findAll()
        return Observable.fromArray(favorites)
    }

    private fun getNextKey(): Int {
        try {
            return realm.where(Favorite::class.java).max(ID).toInt() + 1
        } catch (e: Exception) {
            return 1
        }

    }

}