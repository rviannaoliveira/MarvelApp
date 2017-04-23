package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName
import io.realm.annotations.Ignore

/**
 * Criado por rodrigo on 08/04/17.
 */

class MarvelCharacter : BaseModelMarvel() {
    @SerializedName("urls")
    val urls: ArrayList<MarvelUrl>? = null
    @SerializedName("comics")
    var comics: MarvelComicList? = null
    @SerializedName("stories")
    val stories : MarvelStoryList? = null
    @SerializedName("series")
    val series : MarvelSeriesList? = null
    @Ignore
    var favorite: Favorite? = null
    val comicList: ArrayList<MarvelComic>? = null
}
