package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rcalixto on 20/04/2017.
 */
open class BaseModelMarvelWrapper {
    @SerializedName("code")
    val code: Int? = null
    @SerializedName("status")
    val status: String? = null
}