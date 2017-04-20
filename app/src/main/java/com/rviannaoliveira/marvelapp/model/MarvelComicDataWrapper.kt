package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 14/04/17.
 */
class MarvelComicDataWrapper  : BaseModelMarvelWrapper(){
    @SerializedName("data")
    val data: MarvelComicDataContainer? = null
}