package com.rviannaoliveira.marvelapp.detailCharacter


import com.rviannaoliveira.marvelapp.base.BaseView
import com.rviannaoliveira.marvelapp.model.MarvelComic

/**
 * Criado por rodrigo on 21/04/17.
 */
interface DetailComicView : BaseView {
    fun loadComic(marvelComic: MarvelComic)
}