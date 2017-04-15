package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 14/04/17.
 */
class MarvelCharacterList {
    @SerializedName("available")
    val available: Int? = null
    @SerializedName("returned")
    val returned: Int? = null
    @SerializedName("collectionURI")
    val collectionURI: String? = null
    @SerializedName("items")
    val items: ArrayList<MarvelCharacterSummary>? = null

}