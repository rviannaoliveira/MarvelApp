package com.rviannaoliveira.marvelapp.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.MarvelCharacter

/**
 * Criado por rodrigo on 09/04/17.
 */

class CharactersFragment : Fragment(), CharactersView {
    private val charactersPresenterImpl: CharactersPresenter = CharactersPresenterImpl(this)
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var progressbar: ProgressBar
    private lateinit var charactersRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_characters, container, false)
        this.progressbar = view?.findViewById(R.id.progressbar) as ProgressBar
        this.charactersRecyclerView = view.findViewById(R.id.list_characters) as RecyclerView
        return view
    }

    override fun onStart() {
        super.onStart()
        charactersPresenterImpl.getMarvelCharacters()
    }

    override fun onPause() {
        super.onPause()
        charactersAdapter.clear()
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun loadCharacters(marvelCharacters: ArrayList<MarvelCharacter>) {
        charactersAdapter = CharactersAdapter(marvelCharacters)
        charactersRecyclerView.adapter = charactersAdapter
        charactersRecyclerView.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.line_divider))
        charactersRecyclerView.addItemDecoration(dividerItemDecoration)
    }
}