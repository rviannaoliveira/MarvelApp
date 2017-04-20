package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 14/04/17.
 */
class MarvelComicDataContainer : BaseModelMarvelContainer(){
    @SerializedName("results")
    val results: ArrayList<MarvelComic>? = null
}