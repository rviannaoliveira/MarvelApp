package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
class MarvelSeriesList : BaseModelMarvelList(){
    @SerializedName("items")
    val items : ArrayList<MarvelSeriesSummary>? = null
}