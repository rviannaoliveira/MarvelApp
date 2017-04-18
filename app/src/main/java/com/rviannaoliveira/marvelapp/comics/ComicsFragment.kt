package com.rviannaoliveira.marvelapp.comics

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.MarvelComic
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 14/04/17.
 */


class ComicsFragment : Fragment(), ComicsView {
    private val comicsPresenter: ComicsPresenter = ComicsPresenterImpl(this)
    private lateinit var comicsAdapter: ComicsAdapter
    private lateinit var progressbar: ProgressBar
    private lateinit var comicsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_list, container, false)
        comicsRecyclerView = view?.findViewById(R.id.list_recycler_view) as RecyclerView
        progressbar = view.findViewById(R.id.progressbar) as ProgressBar

        loadView()
        comicsPresenter.getMarvelComics()
        return view
    }

    override fun loadView() {
        comicsAdapter = ComicsAdapter(comicsPresenter)
        comicsRecyclerView.adapter = comicsAdapter
        val numberGrid = if (MarvelUtil.isPortrait(context)) 2 else 3
        comicsRecyclerView.setHasFixedSize(true)
        comicsRecyclerView.layoutManager = GridLayoutManager(context, numberGrid)
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun loadComics(comics: ArrayList<MarvelComic>) {
        comicsAdapter.setComics(comics)
    }

}