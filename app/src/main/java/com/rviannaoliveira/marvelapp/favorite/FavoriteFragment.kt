package com.rviannaoliveira.marvelapp.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.comics.FavoritePresenterImpl
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 15/04/17.
 */
class FavoriteFragment : Fragment(), FavoriteView {
    private val favoritePresenterImpl: FavoritePresenter = FavoritePresenterImpl(this)
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var progressbar: ProgressBar
    private lateinit var favoriteRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_list, container, false)
        favoriteRecyclerView = view?.findViewById(R.id.list_recycler_view) as RecyclerView
        progressbar = view.findViewById(R.id.progressbar) as ProgressBar

        loadView()
        favoritePresenterImpl.getFavorites()
        return view
    }

    override fun loadView() {
        favoriteAdapter = FavoriteAdapter()
        favoriteRecyclerView.adapter = favoriteAdapter
        val numberGrid = if (MarvelUtil.isPortrait(context)) 2 else 3
        favoriteRecyclerView.setHasFixedSize(true)
        favoriteRecyclerView.layoutManager = GridLayoutManager(context, numberGrid)
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun loadFavorite(favorites: ArrayList<Favorite>) {
        favoriteAdapter.setFavorites(favorites)
    }
}