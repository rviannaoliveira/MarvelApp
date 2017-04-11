package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 08/04/17.
 */

class MarvelCharacter {
    @SerializedName("id")
    val id: Int? = null
    @SerializedName("name")
    val name: String? = null
    @SerializedName("description")
    val description: String? = null
    @SerializedName("thumbnail")
    val thumbMail: MarvelImage? = null
    @SerializedName("urls")
    val urls: Array<MarvelUrl>? = null


}
