package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 08/04/17.
 */
class MarvelCharacterDataContainer : BaseModelMarvelContainer(){
    @SerializedName("results")
    var results: ArrayList<MarvelCharacter>? = null
}