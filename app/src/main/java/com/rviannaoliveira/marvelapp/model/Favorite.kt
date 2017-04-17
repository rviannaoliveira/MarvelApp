package com.rviannaoliveira.marvelapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Criado por rodrigo on 15/04/17.
 */
open class Favorite : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var idMarvel: Int? = null
    var name: String? = null
    var path: String? = null
    var extension: String? = null
    var group: Int? = null


    fun getThumbMail(): String {
        return path + extension
    }

}