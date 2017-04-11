package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 08/04/17.
 */
class MarvelCharacterDataContainer {
    @SerializedName("limit")
    val limit: Int? = null
    @SerializedName("count")
    val count: Int? = null
    @SerializedName("total")
    val total: Int? = null
    @SerializedName("results")
    val results: ArrayList<MarvelCharacter>? = null
}