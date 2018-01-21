package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.MarvelApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Criado por rodrigo on 08/04/17.
 */

class MarvelClient {
    private val API_MARVEL_URL = "https://gateway.marvel.com/"
    private val interceptor = HttpLoggingInterceptor()
    private val logging = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor({ chain -> createParametersDefault(chain) })
            .build()


    fun <MarvelService> createService(serviceClass: Class<MarvelService>): MarvelService {
        val builder = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

        if (MarvelApplication.URL == null) {
            builder.baseUrl(API_MARVEL_URL)
        } else {
            builder.baseUrl(MarvelApplication.URL!!)
        }

        val retrofit: Retrofit = builder.client(httpClient).build()
        return retrofit.create(serviceClass)
    }

    private fun createParametersDefault(chain: Interceptor.Chain): Response {
        val timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        var request = chain.request()
        val builder = request.url().newBuilder()

        builder.addQueryParameter("apikey", ApiKey.publicKey)
                .addQueryParameter("hash", MarvelHashGenerate.generate(timeStamp, ApiKey.privateKey, ApiKey.publicKey))
                .addQueryParameter("ts", timeStamp.toString())

        request = request.newBuilder().url(builder.build()).build()
        return chain.proceed(request)

    }
}