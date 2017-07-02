package com.rviannaoliveira.marvelapp.detailComic

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.characters.DetailComicPresenter
import com.rviannaoliveira.marvelapp.detailCharacter.DetailCharacterAdapter
import com.rviannaoliveira.marvelapp.detailCharacter.DetailComicPresenterImpl
import com.rviannaoliveira.marvelapp.detailCharacter.DetailComicView
import com.rviannaoliveira.marvelapp.model.MarvelComic
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 09/04/17.
 */

class DetailComicFragment : Fragment(), DetailComicView {
    private val detailComicPresenterImpl: DetailComicPresenter = DetailComicPresenterImpl(this)
    private lateinit var image: ImageView
    private lateinit var description: TextView
    private lateinit var progressbar: ProgressBar
    private lateinit var appActivity: AppCompatActivity
    private lateinit var blockCharacter: LinearLayout
    private lateinit var reclycerViewCharacter: RecyclerView
    private lateinit var characterAdapter: DetailCharacterAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_comic, container, false)

        appActivity = activity as AppCompatActivity
        appActivity.title = ""
        setHasOptionsMenu(true)

        image = view?.findViewById(R.id.image) as ImageView
        description = view.findViewById(R.id.description) as TextView
        progressbar = view.findViewById(R.id.progressbar) as ProgressBar
        blockCharacter = view.findViewById(R.id.block_character) as LinearLayout
        reclycerViewCharacter = view.findViewById(R.id.list_character) as RecyclerView

        loadView()
        detailComicPresenterImpl.getMarvelComic(arguments.get(MarvelConstant.ID) as Int)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        activity.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun loadView() {
        characterAdapter = DetailCharacterAdapter()
        reclycerViewCharacter.adapter = characterAdapter
        reclycerViewCharacter.setHasFixedSize(true)
        reclycerViewCharacter.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun loadComic(marvelComic: MarvelComic) {
        if (this.isVisible) {
            MarvelUtil.setImageUrl(context, marvelComic.thumbMail?.getPathExtension(), image)
            description.text = if (marvelComic.description?.length == 0) getString(R.string.no_description) else marvelComic.description
            appActivity.supportActionBar?.title = marvelComic.title

            marvelComic.charactersList?.let {
                if (it.isEmpty()) {
                    return
                }
                characterAdapter.setCharacter(it)
                blockCharacter.visibility = View.VISIBLE
            }
        }
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun error() {
        if (this.isVisible) {
            MarvelUtil.showErrorScreen(context, view, resources, R.drawable.ciclope_error)
        }
    }
}