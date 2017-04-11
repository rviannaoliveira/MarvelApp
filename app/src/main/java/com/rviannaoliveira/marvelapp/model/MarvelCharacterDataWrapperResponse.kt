package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 08/04/17.
 */

class MarvelCharacterDataWrapperResponse {
    @SerializedName("code")
    val code: Int? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("data")
    val data: MarvelCharacterDataContainer? = null

}
