package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
open class BaseModelMarvelList {
    @SerializedName("available")
    val available: Int? = null
    @SerializedName("returned")
    val returned: Int? = null
    @SerializedName("collectionURI")
    val collectionURI: String? = null
}