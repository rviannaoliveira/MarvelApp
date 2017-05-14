package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName
import io.realm.annotations.Ignore

/**
 * Criado por rodrigo on 14/04/17.
 */
class MarvelComic : BaseModelMarvel() {
    @SerializedName("pageCount")
    val pageCount: Int? = null
    @SerializedName("urls")
    val urls: ArrayList<MarvelUrl>? = null
    @SerializedName("collections")
    val collections: ArrayList<MarvelComicSummary>? = null
    @SerializedName("images")
    val images: ArrayList<MarvelImage>? = null
    @SerializedName("characters")
    val characters: MarvelCharacterList? = null
    @SerializedName("stories")
    val stories: MarvelStoryList? = null
    @SerializedName("prices")
    val prices: ArrayList<MarvelComicPrice>? = null
    @SerializedName("textObjects")
    val textObjects: ArrayList<MarvelTextObjects>? = null
    @Ignore
    var favorite: Favorite? = null
    var charactersList: ArrayList<MarvelCharacter>? = null
    var storiesList: ArrayList<MarvelStory>? = null
}
