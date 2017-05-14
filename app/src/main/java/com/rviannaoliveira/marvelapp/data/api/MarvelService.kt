package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.MarvelCharacterDataWrapper
import com.rviannaoliveira.marvelapp.model.MarvelComicDataWrapper
import com.rviannaoliveira.marvelapp.model.MarvelSeriesDataWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Criado por rodrigo on 08/04/17.
 */

interface MarvelService {

    @GET("v1/public/characters")
    fun getCharacters(@Query("limit") limit: Int, @Query("offset") offset: Int): Observable<MarvelCharacterDataWrapper>

    @GET("v1/public/comics")
    fun getComics( @Query("limit") limit: Int): Observable<MarvelComicDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int): Observable<MarvelCharacterDataWrapper>

    @GET("/v1/public/comics/{comicId}")
    fun getComic(@Path("comicId") characterId: Int): Observable<MarvelComicDataWrapper>

    @GET
    fun getGenericComic(@Url url: String): Observable<MarvelComicDataWrapper>

    @GET
    fun getGenericSeries(@Url url: String): Observable<MarvelSeriesDataWrapper>

    @GET
    fun getGenericCharacter(@Url url: String): Observable<MarvelCharacterDataWrapper>

}





