package com.rviannaoliveira.marvelapp.data.repository

import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Criado por rodrigo on 15/04/17.
 */
class RepositoryHelper : RepositoryData {
    private val favoriteDao = AppDatabaseFactory.getDefaultInstance().favoriteDao()

    override fun getAllFavorites(): Flowable<List<Favorite>> {
        return favoriteDao.getAll()
    }

    override fun insertFavorite(favorite: Favorite) {
        Single.fromCallable({ favoriteDao.insert(favorite) })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Timber.d("Sucess") },
                        { error -> Timber.w("Error>$error") })
    }

    override fun deleteFavorite(favorite: Favorite) {
        Single.fromCallable({ favoriteDao.delete(favorite) })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Timber.d("Sucess") },
                        { error -> Timber.w("Error>$error") })
    }

    override fun getCharactersFavorites(): Flowable<List<Favorite>> {
        return favoriteDao.getCharactersFavorites()

    }

    override fun getComicsFavorites(): Flowable<List<Favorite>> {
        return favoriteDao.getComicsFavorites()
    }
}