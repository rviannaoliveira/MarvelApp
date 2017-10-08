package com.rviannaoliveira.marvelapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * Criado por rodrigo on 15/04/17.
 */
@Entity
data class Favorite(var idMarvel: Int? = 0) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name: String? = null
    var path: String? = null
    var extension: String? = null
    var groupType: Int? = null
    @Ignore
    val comics = ArrayList<Favorite>()
    @Ignore
    val characters = ArrayList<Favorite>()


    fun getThumbMail(): String {
        return path + "." + extension
    }

}