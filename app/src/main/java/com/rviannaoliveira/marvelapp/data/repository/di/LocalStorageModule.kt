package com.rviannaoliveira.marvelapp.data.repository.di

import android.app.Application
import android.arch.persistence.room.Room
import com.github.salomonbrys.kodein.*
import com.rviannaoliveira.marvelapp.data.repository.AppDatabase
import com.rviannaoliveira.marvelapp.data.repository.FavoriteDao

/**
 * Criado por rodrigo on 21/01/18.
 */
class LocalStorageModule(private val application: Application) {

    val dependenciesKodein = Kodein.Module {
        bind<AppDatabase>(APP_DATABASE_TAG) with eagerSingleton {
            Room.databaseBuilder(application, AppDatabase::class.java, APP_DATABASE_NAME).build()
        }

        bind<FavoriteDao>() with singleton {
            instance<AppDatabase>(APP_DATABASE_TAG).getFavoriteDao()
        }
    }

    companion object {
        val APP_DATABASE_TAG = "database-name"
        val APP_DATABASE_NAME = "app-db"
    }
}