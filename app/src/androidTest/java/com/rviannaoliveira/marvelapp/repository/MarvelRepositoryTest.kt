package com.rviannaoliveira.marvelapp.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.rviannaoliveira.marvelapp.data.repository.AppDatabase
import com.rviannaoliveira.marvelapp.data.repository.FavoriteDao
import com.rviannaoliveira.marvelapp.data.repository.KeyDatabase
import com.rviannaoliveira.marvelapp.model.Favorite
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * Criado por rodrigo on 08/10/17.
 * https://medium.com/google-developers/room-rxjava-acb0cd4f3757
 */
@RunWith(AndroidJUnit4::class)
class MarvelRepositoryTest {
    lateinit var favoriteDao: FavoriteDao
    lateinit var appDatabase: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var favorite: Favorite

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        favoriteDao = appDatabase.getFavoriteDao()
        favorite = getFavorite(1, KeyDatabase.FavoriteGroup.CHARACTERS)
    }

    @Test
    @Throws(Exception::class)
    fun writeFavorite_and_CompareIfReturnTheSame() {
        favoriteDao.insert(favorite)
        favoriteDao.getFavorite(favorite.idMarvel)
                .test()
                .assertValue({ (idMarvel) ->
                    idMarvel == favorite.idMarvel
                })
    }

    @Test
    @Throws(Exception::class)
    fun writeFavorite_and_CompareIfReturnListCharacters() {
        favoriteDao.insert(favorite)
        favoriteDao.getCharactersFavorites()
                .test()
                .assertValue({ (favoriteRoom) ->
                    favoriteRoom.idMarvel == favorite.idMarvel
                })
    }

    private fun getFavorite(idMarvel: Int, groupType: Int): Favorite {
        val favorite = Favorite(idMarvel)
        favorite.groupType = groupType
        return favorite
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }
}