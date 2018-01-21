package com.rviannaoliveira.marvelapp.model

import android.arch.persistence.room.Ignore
import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 21/01/18.
 */

open class BaseModelMarvel {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("thumbnail")
    var thumbMail: MarvelImage? = null
    @SerializedName("title")
    var title: String? = null
}

open class BaseModelMarvelContainer {
    @SerializedName("limit")
    val limit: Int? = null
    @SerializedName("count")
    val count: Int? = null
    @SerializedName("total")
    val total: Int? = null
}

open class BaseModelMarvelList {
    @SerializedName("available")
    val available: Int? = null
    @SerializedName("returned")
    val returned: Int? = null
    @SerializedName("collectionURI")
    val collectionURI: String? = null
}

open class BaseModelMarvelSummary {
    @SerializedName("resourceURI")
    val resourceURI: String? = null
    @SerializedName("name")
    val name: String? = null
}

open class BaseModelMarvelWrapper {
    @SerializedName("code")
    val code: Int? = null
    @SerializedName("status")
    val status: String? = null
}

data class MarvelCharacterDataContainer(
        @SerializedName("results") var results: ArrayList<MarvelCharacter>? = null) : BaseModelMarvelContainer()

data class MarvelCharacterDataWrapper(@SerializedName("data")
                                      var data: MarvelCharacterDataContainer? = null) : BaseModelMarvelWrapper()

data class MarvelCharacterList(@SerializedName("items")
                               val items: ArrayList<MarvelCharacterSummary>? = null) : BaseModelMarvelList()


data class MarvelComicDataWrapper(@SerializedName("data")
                                  val data: MarvelComicDataContainer? = null) : BaseModelMarvelWrapper()

data class MarvelComicList(@SerializedName("items")
                           val items: ArrayList<MarvelComicSummary>? = null) : BaseModelMarvelList()

data class MarvelComicDataContainer(@SerializedName("results")
                                    val results: ArrayList<MarvelComic>? = null) : BaseModelMarvelContainer()

data class MarvelComicPrice(@SerializedName("type")
                            val type: String? = null,
                            @SerializedName("price")
                            val price: Float? = null)

data class MarvelImage(
        @SerializedName("path")
        val path: String? = null,
        @SerializedName("extension")
        val extension: String? = null) {

    fun getPathExtension(): String {
        return path + "." + extension
    }
}

data class MarvelSeries(@SerializedName("endYear")
                        val endYear: Int? = null,
                        @SerializedName("startYear")
                        val startYear: Int? = null) : BaseModelMarvel()

data class MarvelSeriesContainer(@SerializedName("results")
                                 val results: ArrayList<MarvelSeries>? = null) : BaseModelMarvelContainer()

data class MarvelSeriesDataWrapper(@SerializedName("data")
                                   val data: MarvelSeriesContainer? = null) : BaseModelMarvelWrapper()


data class MarvelSeriesList(@SerializedName("items")
                            val items: ArrayList<MarvelSeriesSummary>? = null) : BaseModelMarvelList()

data class MarvelStory(@SerializedName("type")
                       val type: String? = null,
                       @SerializedName("resourceURI")
                       val resourceURI: String? = null) : BaseModelMarvel()

data class MarvelStoryDataContainer(val results: ArrayList<MarvelStory>? = null) : BaseModelMarvelContainer()

data class MarvelStoryDataWrapper(@SerializedName("data")
                                  val data: MarvelStoryDataContainer? = null) : BaseModelMarvelWrapper()

data class MarvelStoryList(@SerializedName("items")
                           val items: ArrayList<MarvelStorySummary>? = null) : BaseModelMarvelList()

data class MarvelStorySummary(@SerializedName("type")
                              val type: String? = null)

data class MarvelTextObjects(@SerializedName("type")
                             val type: String? = null,
                             @SerializedName("name")
                             val text: String? = null,
                             @SerializedName("language")
                             val language: String? = null)

data class MarvelUrl(@SerializedName("type")
                     val type: String? = null,
                     @SerializedName("url")
                     val url: String? = null)

class MarvelComicSummary : BaseModelMarvelSummary()

class MarvelCharacterSummary : BaseModelMarvelSummary()

class MarvelSeriesSummary : BaseModelMarvelSummary()

data class MarvelComic(
        @SerializedName("pageCount")
        val pageCount: Int? = null,
        @SerializedName("urls")
        val urls: ArrayList<MarvelUrl>? = null,
        @SerializedName("collections")
        val collections: ArrayList<MarvelComicSummary>? = null,
        @SerializedName("images")
        val images: ArrayList<MarvelImage>? = null,
        @SerializedName("characters")
        val characters: MarvelCharacterList? = null,
        @SerializedName("stories")
        val stories: MarvelStoryList? = null,
        @SerializedName("prices")
        val prices: ArrayList<MarvelComicPrice>? = null,
        @SerializedName("textObjects")
        val textObjects: ArrayList<MarvelTextObjects>? = null,
        @Ignore
        var favorite: Favorite? = null,
        var charactersList: ArrayList<MarvelCharacter>? = null,
        var storiesList: ArrayList<MarvelStory>? = null
) : BaseModelMarvel()

class MarvelCharacter : BaseModelMarvel {

    constructor() : super()
    constructor(id: Int) : super() {
        this.id = id
    }

    @SerializedName("urls")
    val urls: ArrayList<MarvelUrl>? = null
    @SerializedName("comics")
    var comics: MarvelComicList? = null
    @SerializedName("stories")
    val stories: MarvelStoryList? = null
    @SerializedName("series")
    val series: MarvelSeriesList? = null
    @Ignore
    var favorite: Favorite? = null
    var comicList: ArrayList<MarvelComic>? = null
    var seriesList: ArrayList<MarvelSeries>? = null
}


