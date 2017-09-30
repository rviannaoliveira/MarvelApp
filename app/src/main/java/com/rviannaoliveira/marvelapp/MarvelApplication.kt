package com.rviannaoliveira.marvelapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.rviannaoliveira.marvelapp.data.repository.AppDatabaseFactory
import okhttp3.HttpUrl
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Criado por rodrigo on 15/04/17.
 */
class MarvelApplication : Application() {
    var context: MarvelApplication? = null

    override fun onCreate() {
        super.onCreate()
        context = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            initStetho()
        }
        AppDatabaseFactory.init(this)
    }

    private fun initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .build())
    }


    companion object {
        var URL: HttpUrl? = null
    }
}