package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 14/04/17.
 */
class MarvelComic : BaseMarvel() {

    @SerializedName("title")
    val title: String? = null
    @SerializedName("description")
    val description: String? = null
    @SerializedName("pageCount")
    val pageCount: Int? = null
    @SerializedName("ulrs")
    val urls: ArrayList<MarvelUrl>? = null
    @SerializedName("collections")
    val collections: ArrayList<MarvelComicSummary>? = null
    @SerializedName("thumbnail")
    val thumbMail: MarvelImage? = null
    @SerializedName("images")
    val images: ArrayList<MarvelImage>? = null
    @SerializedName("characters")
    val characters: MarvelCharacterList? = null
    @SerializedName("prices")
    val prices: ArrayList<MarvelComicPrice>? = null
    @SerializedName("textObjects")
    val textObjects: ArrayList<MarvelTextObjects>? = null
}
