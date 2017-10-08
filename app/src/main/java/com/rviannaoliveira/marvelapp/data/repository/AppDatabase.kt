package com.rviannaoliveira.marvelapp.data.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.rviannaoliveira.marvelapp.model.Favorite


/**
 * Criado por rodrigo on 30/09/17.
 */
@Database(entities = arrayOf(Favorite::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}