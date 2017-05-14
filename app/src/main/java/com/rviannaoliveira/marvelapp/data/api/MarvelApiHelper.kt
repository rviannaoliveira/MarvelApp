package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.*
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers


/**
 * Criado por rodrigo on 09/04/17.
 */

class MarvelApiHelper : ApiData {
    private var marvelService: MarvelService = MarvelClient().createService(MarvelService::class.java)
    private var LIMIT_REGISTER = 30
    private var charactersCache = ArrayList<MarvelCharacter>()
    private var comicsCache = ArrayList<MarvelComic>()
    private var detailCharacterCache = HashMap<Int, MarvelCharacter>()
    private var detailComicCache = HashMap<Int, MarvelComic>()


    override fun getMarvelCharacters(offset: Int): Observable<ArrayList<MarvelCharacter>> {
        if (offset == 0 && charactersCache.isNotEmpty()) {
            return Observable.fromArray(charactersCache)
        }

        val response = marvelService.getCharacters(LIMIT_REGISTER, offset)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    results?.let { charactersCache.addAll(it) }
                    Observable.fromArray(results)
                })
    }

    override fun getMarvelComics(offset: Int): Observable<ArrayList<MarvelComic>> {
        if (offset == 0 && comicsCache.isNotEmpty()) {
            return Observable.fromArray(comicsCache)
        }

        val response = marvelService.getComics(LIMIT_REGISTER, offset)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    results?.let { comicsCache.addAll(it) }
                    Observable.fromArray(dataWrappers.data?.results)
                })
    }

    override fun getDetailCharacter(id: Int): Observable<MarvelCharacter> {
        if (detailCharacterCache.containsKey(id)) {
            return Observable.just(detailCharacterCache[id])
        }

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
                            getMarvelCharacterSeries(data),
                            Function3<MarvelCharacter?, MarvelComicDataWrapper, MarvelSeriesDataWrapper, MarvelCharacter>({ character, comics, series ->
                                character?.comicList = comics.data?.results
                                character?.seriesList = series.data?.results
                                character?.id?.let { detailCharacterCache.put(it, character) }
                                character
                            }))
                })
    }

    override fun getDetailComic(id: Int): Observable<MarvelComic> {
        if (detailComicCache.containsKey(id)) {
            return Observable.just(detailComicCache[id])
        }

        val response = marvelService.getComic(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Observable.just(dataWrappers.data?.results?.get(0))
                })
                .flatMap({ data ->
                    Observable.zip(Observable.just(data),
                            getMarvelComicCharacter(data),
                            BiFunction<MarvelComic?, MarvelCharacterDataWrapper, MarvelComic>({ comic, characters ->
                                comic?.charactersList = characters.data?.results
                                comic?.id?.let { detailComicCache.put(it, comic) }
                                comic
                            }))
                })
    }

    private fun getMarvelCharacterComics(data: MarvelCharacter?): ObservableSource<MarvelComicDataWrapper>? {
        return data?.comics?.collectionURI?.let {
            marvelService.getGenericComic(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelCharacterSeries(data: MarvelCharacter?): ObservableSource<MarvelSeriesDataWrapper>? {
        return data?.series?.collectionURI?.let {
            marvelService.getGenericSeries(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelComicCharacter(data: MarvelComic?): ObservableSource<MarvelCharacterDataWrapper>? {
        return data?.characters?.collectionURI?.let {
            marvelService.getGenericCharacter(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}