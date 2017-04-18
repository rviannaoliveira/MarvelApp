package com.rviannaoliveira.marvelapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
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

    fun initStetho() {
        Realm.init(this)
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}