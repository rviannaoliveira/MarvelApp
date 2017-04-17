package com.rviannaoliveira.marvelapp

import android.app.Application
import io.realm.Realm

/**
 * Criado por rodrigo on 15/04/17.
 */
class MarvelApplication : Application() {
    var context: MarvelApplication? = null

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        context = this
    }
}