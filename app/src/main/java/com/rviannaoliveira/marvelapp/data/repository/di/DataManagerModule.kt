package com.rviannaoliveira.marvelapp.data.repository.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.rviannaoliveira.marvelapp.data.DataManager
import com.rviannaoliveira.marvelapp.data.IDataManager
import com.rviannaoliveira.marvelapp.data.api.IApiData
import com.rviannaoliveira.marvelapp.data.api.MarvelApiHelper
import com.rviannaoliveira.marvelapp.data.repository.IRepositoryData
import com.rviannaoliveira.marvelapp.data.repository.RepositoryHelper

/**
 * Criado por rodrigo on 21/01/18.
 */

class DataManagerModule {
    val dependenciesKodein = Kodein.Module {

        bind<IDataManager>() with singleton {
            DataManager(
                    apiData = instance(),
                    repositoryData = instance()
            )
        }

        bind<IApiData>() with singleton {
            MarvelApiHelper()
        }

        bind<IRepositoryData>() with singleton {
            RepositoryHelper(favoriteDao = instance())
        }
    }
}