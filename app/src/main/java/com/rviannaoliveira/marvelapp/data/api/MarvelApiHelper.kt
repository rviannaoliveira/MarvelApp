package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.model.MarvelComic
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
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
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Observable.fromArray(dataWrappers.data)
                })
                .concatMap({ dataContainer ->
                    dataContainer?.let { Observable.just(dataContainer.results?.get(0)) }
                })
        //TODO pensar numa forma melhor
//                .flatMap({ data ->
//                   data?.comicList = marvelService.getCharacterComic(data?.id)
//                            .concatMap({ dataWrappers ->
//                                Observable.fromArray(dataWrappers.data)
//                            })
//                            .concatMap({ dataContainer ->
//                                dataContainer?.let { Observable.fromArray(dataContainer.results)}
//                            }) // Aqui que me perdi -.-
//                })
    }

}