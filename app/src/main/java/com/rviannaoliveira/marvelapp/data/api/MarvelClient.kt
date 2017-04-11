package com.rviannaoliveira.marvelapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Criado por rodrigo on 08/04/17.
 */

class MarvelClient {
    private val API_MARVEL_URL = "https://gateway.marvel.com/"
    private val interceptor = HttpLoggingInterceptor()
    private val logging = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    private val builder = Retrofit.Builder()
            .baseUrl(API_MARVEL_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

    fun <MarvelService> createService(serviceClass: Class<MarvelService>): MarvelService {
        val retrofit: Retrofit = builder.client(httpClient).build()
        return retrofit.create(serviceClass)
    }


}