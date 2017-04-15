package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelComic
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Criado por rodrigo on 09/04/17.
 */

class MarvelApiHelper {
    private var marvelService: MarvelService = MarvelClient().createService(MarvelService::class.java)
    private var LIMIT_REGISTER = 10

    fun getMarvelCharacters(): Observable<ArrayList<MarvelCharacter>> {
        val response = marvelService.getCharacters(getMapDefaultParams(), LIMIT_REGISTER)

        return response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Observable.fromArray(dataWrappers.data)
                })
                .concatMap({ dataContainer ->
                    Observable.fromArray(dataContainer?.results)
                })

    }


    fun getMarvelComics(): Observable<ArrayList<MarvelComic>> {
        val response = marvelService.getComics(getMapDefaultParams(), LIMIT_REGISTER)
        return response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Observable.fromArray(dataWrappers.data)
                })
                .concatMap({ dataContainer ->
                    Observable.fromArray(dataContainer?.results)
                })
    }


    fun getMapDefaultParams(): Map<String, String> {
        val timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        val data = HashMap<String, String>()

        data.put("apikey", ApiKey.publicKey)
        data.put("hash", MarvelHashGenerate.generate(timeStamp, ApiKey.privateKey, ApiKey.publicKey))
        data.put("ts", timeStamp.toString())
        return data

    }


}