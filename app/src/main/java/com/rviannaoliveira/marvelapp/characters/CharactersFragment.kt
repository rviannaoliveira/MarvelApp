package com.rviannaoliveira.marvelapp.characters

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.os.Parcelable
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
    private var isLoading: Boolean = false
    private val LIST_STATE_KEY = "123"
    private var listState: Parcelable? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_list, container, false)
        this.progressbar = view?.findViewById(R.id.progressbar) as ProgressBar
        this.charactersRecyclerView = view.findViewById(R.id.list_recycler_view) as RecyclerView

        loadView()
        charactersPresenterImpl.getMarvelCharacters(0)
        return view
    }

    override fun onResume() {
        super.onResume()
        charactersRecyclerView.layoutManager.onRestoreInstanceState(listState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        listState = savedInstanceState?.getParcelable<Parcelable>(LIST_STATE_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(LIST_STATE_KEY, charactersRecyclerView.layoutManager.onSaveInstanceState())
    }

    override fun loadView() {
        charactersAdapter = CharactersAdapter(charactersPresenterImpl, activity as AppCompatActivity)
        charactersRecyclerView.adapter = charactersAdapter
        val numberGrid = if (MarvelUtil.isPortrait(context)) 2 else 3
        charactersRecyclerView.setHasFixedSize(true)
        charactersRecyclerView.layoutManager = GridLayoutManager(context, numberGrid)
        charactersRecyclerView.addOnScrollListener(onScrollListener())
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun loadCharacters(marvelCharacters: ArrayList<MarvelCharacter>) {
        charactersAdapter?.setCharacters(marvelCharacters)
        isLoading = false
    }

    override fun error() {
        if (this.isVisible) {
            MarvelUtil.showErrorScreen(context, view, resources, R.drawable.deadpool_error)
        }
    }

    private fun onScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = recyclerView.layoutManager.childCount
                val totalItemCount = recyclerView.layoutManager.itemCount
                val firstVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                if (!isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    isLoading = true
                    charactersPresenterImpl.getMarvelCharacters(totalItemCount)
                }
            }
        }
    }

}