package com.rviannaoliveira.marvelapp.detailCharacter.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.rviannaoliveira.marvelapp.detailCharacter.ui.DetailCharacterPresenter
import com.rviannaoliveira.marvelapp.detailCharacter.ui.DetailCharacterPresenterImpl
import com.rviannaoliveira.marvelapp.detailCharacter.ui.DetailCharacterView

/**
 * Criado por rodrigo on 21/01/18.
 */
class DetailCharacterModule(private val view: DetailCharacterView) {

    val dependenciesKodein = Kodein.Module {
        bind<DetailCharacterPresenter>() with provider {
            DetailCharacterPresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}