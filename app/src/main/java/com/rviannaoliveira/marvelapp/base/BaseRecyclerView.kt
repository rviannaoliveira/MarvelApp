package com.rviannaoliveira.marvelapp.base

import android.view.View

/**
 * Criado por rodrigo on 17/04/17.
 */
interface BaseRecyclerView {
    fun toggleFavorite(position: Int, view: View)
    fun toggleImage(checked: Boolean): Int
}