package com.rviannaoliveira.marvelapp.characters.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.rviannaoliveira.marvelapp.characters.ui.CharactersPresenter
import com.rviannaoliveira.marvelapp.characters.ui.CharactersPresenterImpl
import com.rviannaoliveira.marvelapp.characters.ui.CharactersView

/**
 * Criado por rodrigo on 21/01/18.
 */
class CharactersModule(private val view: CharactersView) {

    val dependenciesKodein = Kodein.Module {
        bind<CharactersPresenter>() with provider {
            CharactersPresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}