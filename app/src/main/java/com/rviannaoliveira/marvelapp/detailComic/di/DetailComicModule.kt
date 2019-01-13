package com.rviannaoliveira.marvelapp.detailComic.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.rviannaoliveira.marvelapp.detailCharacter.DetailComicView
import com.rviannaoliveira.marvelapp.detailComic.DetailComicPresenter
import com.rviannaoliveira.marvelapp.detailComic.DetailComicPresenterImpl

/**
 * Criado por rodrigo on 21/01/18.
 */
class DetailComicModule(private val view: DetailComicView) {

    val dependenciesKodein = Kodein.Module {
        bind<DetailComicPresenter>() with provider {
            DetailComicPresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}