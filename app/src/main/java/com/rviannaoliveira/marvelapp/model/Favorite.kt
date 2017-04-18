package com.rviannaoliveira.marvelapp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Criado por rodrigo on 15/04/17.
 */
open class Favorite : RealmObject() {
    @PrimaryKey
    open var id: Int? = null
    open var idMarvel: Int? = null
    open var name: String? = null
    open var path: String? = null
    open var extension: String? = null
    open var group: Int? = null


    fun getThumbMail(): String {
        return path + "." + extension
    }

}