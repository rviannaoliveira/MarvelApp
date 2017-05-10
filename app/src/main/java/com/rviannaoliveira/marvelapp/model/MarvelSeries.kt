package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
class MarvelSeries : BaseModelMarvel(){
    @SerializedName("endYear")
    val endYear: Int? = null
    @SerializedName("startYear")
    val startYear: Int? = null
}