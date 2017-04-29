package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelComic
import com.rviannaoliveira.marvelapp.model.MarvelComicDataWrapper
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


/**
 * Criado por rodrigo on 09/04/17.
 */

class MarvelApiHelper : ApiData {
    private var marvelService: MarvelService = MarvelClient().createService(MarvelService::class.java)
    private var LIMIT_REGISTER = 20

    override fun getMarvelCharacters(): Observable<ArrayList<MarvelCharacter>> {
        val response = marvelService.getCharacters(LIMIT_REGISTER)

        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Observable.fromArray(dataWrappers.data)
                })
                .concatMap({ dataContainer ->
                    Observable.fromArray(dataContainer?.results)
                })
    }

    override fun getMarvelComics(): Observable<ArrayList<MarvelComic>> {
        val response = marvelService.getComics(LIMIT_REGISTER)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Observable.fromArray(dataWrappers.data)
                })
                .concatMap({ dataContainer ->
                    Observable.fromArray(dataContainer?.results)
                })
    }

    override fun getDetailCharacter(id: Int): Observable<MarvelCharacter> {
        val response = marvelService.getCharacter(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Observable.just(dataWrappers.data?.results?.get(0))
                })
                .flatMap({ data ->
                    Observable.zip(Observable.just(data),
                            getMarvelCharacterComics(data),
                            BiFunction<MarvelCharacter?, MarvelComicDataWrapper, MarvelCharacter>({ character, wrapper ->
                                character?.comicList = wrapper.data?.results
                                character
                            }))
                })
    }

    private fun getMarvelCharacterComics(data: MarvelCharacter?): ObservableSource<MarvelComicDataWrapper>? {
        return data?.comics?.collectionURI?.let {
            marvelService.getCharacterComic(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}