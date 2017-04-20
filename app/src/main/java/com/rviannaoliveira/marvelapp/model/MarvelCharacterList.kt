package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 14/04/17.
 */
class MarvelCharacterList : BaseModelMarvelList(){
    @SerializedName("items")
    val items: ArrayList<MarvelCharacterSummary>? = null

}