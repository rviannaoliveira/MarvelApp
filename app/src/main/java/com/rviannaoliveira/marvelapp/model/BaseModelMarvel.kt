package com.rviannaoliveira.marvelapp.model

import com.google.gson.annotations.SerializedName
import io.realm.annotations.Ignore

/**
 * Criado por rodrigo on 16/04/17.
 */
open class BaseModelMarvel {
    @SerializedName("id")
    val id: Int? = null
    @Ignore
    var favorite: Favorite? = null
}