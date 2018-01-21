package com.rviannaoliveira.marvelapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.rviannaoliveira.marvelapp.di.DependenciesMarvel
import okhttp3.HttpUrl
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Criado por rodrigo on 15/04/17.
 */
class MarvelApplication : Application(), KodeinAware {
    var context: MarvelApplication? = null


    override fun onCreate() {
        super.onCreate()
        context = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            initStetho()
        }
    }


    private fun initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .build())
    }

    override val kodein: Kodein by DependenciesMarvel(this).dependenciesGraph


    companion object {
        var URL: HttpUrl? = null
    }
}