package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
open class BaseModelMarvelContainer {
    @SerializedName("limit")
    val limit: Int? = null
    @SerializedName("count")
    val count: Int? = null
    @SerializedName("total")
    val total: Int? = null
}