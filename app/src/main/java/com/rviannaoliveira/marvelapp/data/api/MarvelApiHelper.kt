package com.rviannaoliveira.marvelapp.data.api

import com.rviannaoliveira.marvelapp.model.*
import io.reactivex.Single
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


    override fun getMarvelCharacters(offset: Int): Single<List<MarvelCharacter>> {
        if (offset == 0 && charactersCache.isNotEmpty()) {
            return Single.just(charactersCache)
        }

        val response = marvelService.getCharacters(LIMIT_REGISTER, offset)
        return response
                .subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .map({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    results?.let { charactersCache.addAll(it) }
                    results
                })
    }

    override fun getMarvelCharactersBeginLetter(letter: String): Single<List<MarvelCharacter>> {

        val response = marvelService.getCharactersBeginLetter(100, letter)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .map({ dataWrappers ->
                    dataWrappers.data?.results
                })
    }

    override fun getMarvelComicsBeginLetter(letter: String): Single<List<MarvelComic>> {
        val response = marvelService.getComicsBeginLetter(100, letter)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .map({ dataWrappers ->
                    dataWrappers.data?.results
                })
    }

    override fun getMarvelComics(offset: Int): Single<List<MarvelComic>> {
        if (offset == 0 && comicsCache.isNotEmpty()) {
            return Single.just(comicsCache)
        }

        val response = marvelService.getComics(LIMIT_REGISTER, offset)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .map({ dataWrappers ->
                    val results = dataWrappers.data?.results
                    results?.let { comicsCache.addAll(it) }
                    dataWrappers.data?.results
                })
    }

    override fun getDetailCharacter(id: Int?): Single<MarvelCharacter> {
        if (detailCharacterCache.containsKey(id)) {
            return Single.just(detailCharacterCache[id])
        }

        val response = marvelService.getCharacter(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .map({ dataWrappers ->
                    dataWrappers.data?.results?.get(0)
                })
                .flatMap({ data ->
                    Single.zip(Single.just(data),
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

    override fun getDetailComic(id: Int?): Single<MarvelComic> {
        if (detailComicCache.containsKey(id)) {
            return Single.just(detailComicCache[id])
        }

        val response = marvelService.getComic(id)
        return response.subscribeOn(Schedulers.newThread())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread())
                .map { dataWrappers ->
                    dataWrappers.data?.results?.get(0)
                }
                .flatMap { data ->
                    Single.zip(Single.just(data),
                            getMarvelComicCharacter(data),
                            BiFunction<MarvelComic?, MarvelCharacterDataWrapper, MarvelComic> { comic, characters ->
                                comic.charactersList = characters.data?.results
                                comic.id?.let { detailComicCache.put(it, comic) }
                                comic
                            })
                }
    }

    private fun getMarvelCharacterComics(data: MarvelCharacter?): Single<MarvelComicDataWrapper>? {
        return data?.comics?.collectionURI?.let {
            marvelService.getGenericComic(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelCharacterSeries(data: MarvelCharacter?): Single<MarvelSeriesDataWrapper>? {
        return data?.series?.collectionURI?.let {
            marvelService.getGenericSeries(it)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getMarvelComicCharacter(data: MarvelComic?): Single<MarvelCharacterDataWrapper>? {
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