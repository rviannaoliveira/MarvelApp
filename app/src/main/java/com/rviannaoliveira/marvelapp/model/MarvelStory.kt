package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */

class MarvelStory : BaseModelMarvel(){
    @SerializedName("type")
    val type : String? = null
    @SerializedName("resourceURI")
    val resourceURI : String? = null
}
