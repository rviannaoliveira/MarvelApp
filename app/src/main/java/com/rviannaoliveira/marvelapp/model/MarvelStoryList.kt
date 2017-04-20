package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
class MarvelStoryList : BaseModelMarvelList(){
    @SerializedName("items")
    val items : ArrayList<MarvelStorySummary>? = null

}