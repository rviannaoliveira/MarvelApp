package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName

/**
 * Criado por rodrigo on 08/04/17.
 */
class MarvelImage {
    @SerializedName("path")
    val path: String? = null
    @SerializedName("extension")
    val extension: String? = null


    fun getPathExtension(): String {
        return path + "." + extension
    }
}