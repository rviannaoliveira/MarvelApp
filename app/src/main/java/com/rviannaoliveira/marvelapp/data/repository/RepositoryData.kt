package com.rviannaoliveira.marvelapp.data.repository

import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.Flowable

/**
 * Criado por rodrigo on 15/04/17.
 */
interface RepositoryData {
    fun getAllFavorites(): Flowable<List<Favorite>>
    fun getCharactersFavorites(): Flowable<List<Favorite>>
    fun getComicsFavorites(): Flowable<List<Favorite>>
    fun insertFavorite(favorite: Favorite)
    fun deleteFavorite(favorite: Favorite)

}