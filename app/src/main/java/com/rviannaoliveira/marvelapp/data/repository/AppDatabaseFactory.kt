package com.rviannaoliveira.marvelapp.data.repository

import android.arch.persistence.room.Room
import android.content.Context

/**
 * Criado por rodrigo on 30/09/17.
 */
object AppDatabaseFactory {
    private var appDatabase: AppDatabase? = null

    fun init(context: Context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
        }
    }

    fun getDefaultInstance(): AppDatabase {
        if (appDatabase == null) {
            throw IllegalStateException("Call `RoomDatabase.init(Context)` before calling this method.")
        } else {
            return appDatabase as AppDatabase
        }
    }
}