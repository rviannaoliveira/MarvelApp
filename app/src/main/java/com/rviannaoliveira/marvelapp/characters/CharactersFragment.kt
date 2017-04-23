package com.rviannaoliveira.marvelapp.characters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 09/04/17.
 */

class CharactersFragment : Fragment(), CharactersView {
    private val charactersPresenterImpl: CharactersPresenter = CharactersPresenterImpl(this)
    private var charactersAdapter: CharactersAdapter? = null
    private lateinit var progressbar: ProgressBar
    private lateinit var charactersRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_list, container, false)
        this.progressbar = view?.findViewById(R.id.progressbar) as ProgressBar
        this.charactersRecyclerView = view.findViewById(R.id.list_recycler_view) as RecyclerView

        loadView()
        charactersPresenterImpl.getMarvelCharacters()
        return view
    }

    override fun loadView() {
        charactersAdapter = CharactersAdapter(charactersPresenterImpl, activity as AppCompatActivity)
        charactersRecyclerView.adapter = charactersAdapter
        val numberGrid = if (MarvelUtil.isPortrait(context)) 2 else 3
        charactersRecyclerView.setHasFixedSize(true)
        charactersRecyclerView.layoutManager = GridLayoutManager(context, numberGrid)
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun loadCharacters(marvelCharacters: ArrayList<MarvelCharacter>) {
        charactersAdapter?.setCharacters(marvelCharacters)
    }
}