package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
class MarvelSeries : BaseModelMarvel(){
    @SerializedName("rating")
    val rating : String? = null
    @SerializedName("urls")
    val urls : MarvelUrl? =null
}