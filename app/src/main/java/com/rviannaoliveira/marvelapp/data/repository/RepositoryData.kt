package com.rviannaoliveira.marvelapp.data.repository

import com.rviannaoliveira.marvelapp.model.Favorite
import io.reactivex.Observable

/**
 * Criado por rodrigo on 15/04/17.
 */
interface RepositoryData {
    fun getAllFavorites(): Observable<Favorite>
    fun getCharactersFavorites(): Observable<List<Favorite>>
    fun getComicsFavorites(): Observable<List<Favorite>>
    fun insertFavorite(favorite: Favorite)
    fun deleteFavorite(favorite: Favorite)

}