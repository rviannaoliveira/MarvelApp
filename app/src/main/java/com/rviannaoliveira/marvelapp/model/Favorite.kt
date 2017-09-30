package com.rviannaoliveira.marvelapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Criado por rodrigo on 15/04/17.
 */
@Entity
open class Favorite {
    @PrimaryKey(autoGenerate = true)
    open var id: Int? = null
    open var idMarvel: Int? = null
    open var name: String? = null
    open var path: String? = null
    open var extension: String? = null
    open var group: Int? = null
    @Ignore
    val comics = ArrayList<Favorite>()
    @Ignore
    val characters = ArrayList<Favorite>()


    fun getThumbMail(): String {
        return path + "." + extension
    }

}