package com.rviannaoliveira.marvelapp.comics.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.rviannaoliveira.marvelapp.comics.ui.ComicsPresenter
import com.rviannaoliveira.marvelapp.comics.ui.ComicsPresenterImpl
import com.rviannaoliveira.marvelapp.comics.ui.ComicsView

/**
 * Criado por rodrigo on 21/01/18.
 */
class ComicModule(private val view: ComicsView) {

    val dependenciesKodein = Kodein.Module {
        bind<ComicsPresenter>() with provider {
            ComicsPresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}