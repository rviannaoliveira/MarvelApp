package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.MarvelCharacterDataWrapperResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Criado por rodrigo on 08/04/17.
 */

interface MarvelService {

    @GET("v1/public/characters")
    fun getCharacters(@QueryMap options: Map<String, String>, @Query("limit") limit: Int): Observable<MarvelCharacterDataWrapperResponse>
}





