package com.rviannaoliveira.marvelapp.character


import com.rviannaoliveira.marvelapp.base.BaseView
import com.rviannaoliveira.marvelapp.model.MarvelCharacter

/**
 * Criado por rodrigo on 21/04/17.
 */
interface DetailCharacterView : BaseView {
    fun loadCharacter(marvelCharacters: MarvelCharacter)
}