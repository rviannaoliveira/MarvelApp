package com.rviannaoliveira.marvelapp.favorite.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.rviannaoliveira.marvelapp.favorite.FavoritePresenter
import com.rviannaoliveira.marvelapp.favorite.FavoritePresenterImpl
import com.rviannaoliveira.marvelapp.favorite.FavoriteView

/**
 * Criado por rodrigo on 21/01/18.
 */
class FavoriteModule(private val view: FavoriteView) {

    val dependenciesKodein = Kodein.Module {
        bind<FavoritePresenter>() with provider {
            FavoritePresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}