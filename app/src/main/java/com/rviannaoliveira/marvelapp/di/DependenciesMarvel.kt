package com.rviannaoliveira.marvelapp.di

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.lazy
import com.rviannaoliveira.marvelapp.data.repository.di.DataManagerModule
import com.rviannaoliveira.marvelapp.data.repository.di.LocalStorageModule

/**
 * Criado por rodrigo on 21/01/18.
 */
class DependenciesMarvel(private val application: Application) {

    val dependenciesGraph = Kodein.lazy {
        import(LocalStorageModule(application).dependenciesKodein)
        import(DataManagerModule().dependenciesKodein)
    }

}