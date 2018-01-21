package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.*
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers


/**
 * Criado por rodrigo on 09/04/17.
 */

class MarvelApiHelper(private var marvelService: MarvelService = MarvelClient().createService(MarvelService::class.java)) : IApiData {

    companion object {
        var charactersCache = ArrayList<MarvelCharacter>()
        var comicsCache = ArrayList<MarvelComic>()
        var detailCharacterCache = HashMap<Int, MarvelCharacter>()
        var LIMIT_REGISTER = 30
        var detailComicCache = HashMap<Int, MarvelComic>()
    }


    override fun getMarvelCharacters(offset: Int): Flowable<List<MarvelCharacter>> {
        if (offset == 0 && charactersCache.isNotEmpty()) {
            return Flowable.fromArray(charactersCache)
        }

        val response = marvelService.getCharacters(LIMIT_REGISTER, offset)
        return response
                .subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    results?.let { charactersCache.addAll(it) }
                    Flowable.fromArray(results)
                })
    }

    override fun getMarvelCharactersBeginLetter(letter: String): Flowable<List<MarvelCharacter>> {

        val response = marvelService.getCharactersBeginLetter(100, letter)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    Flowable.fromArray(results)
                })
    }

    override fun getMarvelComicsBeginLetter(letter: String): Flowable<List<MarvelComic>> {
        val response = marvelService.getComicsBeginLetter(100, letter)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    Flowable.fromArray(results)
                })
    }

    override fun getMarvelComics(offset: Int): Flowable<List<MarvelComic>> {
        if (offset == 0 && comicsCache.isNotEmpty()) {
            return Flowable.fromArray(comicsCache)
        }

        val response = marvelService.getComics(LIMIT_REGISTER, offset)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    results?.let { comicsCache.addAll(it) }
                    Flowable.fromArray(dataWrappers.data?.results)
                })
    }

    override fun getDetailCharacter(id: Int?): Flowable<MarvelCharacter> {
        if (detailCharacterCache.containsKey(id)) {
            return Flowable.just(detailCharacterCache[id])
        }

        val response = marvelService.getCharacter(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Flowable.just(dataWrappers.data?.results?.get(0))
                })
                .flatMap({ data ->
                    Flowable.zip(Flowable.just(data),
                            getMarvelCharacterComics(data),
                            getMarvelCharacterSeries(data),
                            Function3<MarvelCharacter?, MarvelComicDataWrapper, MarvelSeriesDataWrapper, MarvelCharacter>({ character, comics, series ->
                                character.comicList = comics.data?.results
                                character.seriesList = series.data?.results
                                character.id?.let { detailCharacterCache.put(it, character) }
                                character
                            }))
                })
    }

    override fun getDetailComic(id: Int?): Flowable<MarvelComic> {
        if (detailComicCache.containsKey(id)) {
            return Flowable.just(detailComicCache[id])
        }

        val response = marvelService.getComic(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap({ dataWrappers ->
                    Flowable.just(dataWrappers.data?.results?.get(0))
                })
                .flatMap({ data ->
                    Flowable.zip(Flowable.just(data),
                            getMarvelComicCharacter(data),
                            BiFunction<MarvelComic?, MarvelCharacterDataWrapper, MarvelComic>({ comic, characters ->
                                comic.charactersList = characters.data?.results
                                comic.id?.let { detailComicCache.put(it, comic) }
                                comic
                            }))
                })
    }

    private fun getMarvelCharacterComics(data: MarvelCharacter?): Flowable<MarvelComicDataWrapper>? {
        return data?.comics?.collectionURI?.let {
            marvelService.getGenericComic(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelCharacterSeries(data: MarvelCharacter?): Flowable<MarvelSeriesDataWrapper>? {
        return data?.series?.collectionURI?.let {
            marvelService.getGenericSeries(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelComicCharacter(data: MarvelComic?): Flowable<MarvelCharacterDataWrapper>? {
        return data?.characters?.collectionURI?.let {
            marvelService.getGenericCharacter(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    override fun removeFavoriteCharacterCache(idMarvel: Int?) {
        charactersCache.forEach { item ->
            if (item.id == idMarvel) {
                item.favorite = null
            }
        }
    }

    override fun removeFavoriteComicCache(idMarvel: Int?) {
        comicsCache.forEach { item ->
            if (item.id == idMarvel) {
                item.favorite = null

            }
        }
    }

}