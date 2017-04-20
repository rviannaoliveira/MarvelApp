package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
class MarvelStoryDataWrapper  : BaseModelMarvelWrapper(){
    @SerializedName("data")
    val data: MarvelStoryDataContainer? = null

}