package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 14/04/17.
 */
class MarvelComicDataWrapper {
    @SerializedName("code")
    val code: Int? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("data")
    val data: MarvelComicDataContainer? = null
}