package com.rviannaoliveira.marvelapp.comics

import com.rviannaoliveira.marvelapp.base.BaseView
import com.rviannaoliveira.marvelapp.model.MarvelComic

/**
 * Criado por rodrigo on 14/04/17.
 */
interface ComicsView : BaseView {
    fun loadComics(comics: ArrayList<MarvelComic>)
}