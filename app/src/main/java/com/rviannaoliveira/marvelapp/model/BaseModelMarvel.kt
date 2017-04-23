package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 16/04/17.
 */
open class BaseModelMarvel {
    @SerializedName("id")
    val id: Int? = null
    @SerializedName("name")
    val name: String? = null
    @SerializedName("description")
    val description: String? = null
    @SerializedName("thumbnail")
    val thumbMail: MarvelImage? = null
    @SerializedName("title")
    val title: String? = null
}