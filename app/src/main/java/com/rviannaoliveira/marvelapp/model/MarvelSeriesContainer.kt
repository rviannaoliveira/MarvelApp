package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
class MarvelSeriesContainer : BaseModelMarvelContainer(){
    @SerializedName("results")
    val results: ArrayList<MarvelSeries>? = null
}