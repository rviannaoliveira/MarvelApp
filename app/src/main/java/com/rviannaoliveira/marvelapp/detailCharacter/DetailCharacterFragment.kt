package com.rviannaoliveira.marvelapp.characters

import android.graphics.BitmapFactory
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
import com.rviannaoliveira.marvelapp.detailCharacter.DetailCharacterPresenterImpl
import com.rviannaoliveira.marvelapp.detailCharacter.DetailCharacterView
import com.rviannaoliveira.marvelapp.detailCharacter.DetailComicsAdapter
import com.rviannaoliveira.marvelapp.detailCharacter.DetailSeriesAdapter
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 09/04/17.
 */

class DetailCharacterFragment : Fragment(), DetailCharacterView {
    private val detailCharacterPresenterImpl: DetailCharacterPresenter = DetailCharacterPresenterImpl(this)
    private lateinit var image: ImageView
    private lateinit var description: TextView
    private lateinit var title: TextView
    private lateinit var progressbar: ProgressBar
    private lateinit var appActivity: AppCompatActivity
    private lateinit var blockComics: LinearLayout
    private lateinit var blockSeries: LinearLayout
    private lateinit var reclycerViewComic: RecyclerView
    private lateinit var reclycerViewSeries: RecyclerView
    private lateinit var comicsAdapter: DetailComicsAdapter
    private lateinit var seriesAdapter: DetailSeriesAdapter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_character, container, false)
        appActivity = activity as AppCompatActivity
        appActivity.title = ""
        setHasOptionsMenu(true)

        image = view?.findViewById(R.id.image) as ImageView
        description = view.findViewById(R.id.description) as TextView
        progressbar = view.findViewById(R.id.progressbar) as ProgressBar
        title = view.findViewById(R.id.title) as TextView
        blockComics = view.findViewById(R.id.block_comics) as LinearLayout
        blockSeries = view.findViewById(R.id.block_series) as LinearLayout
        reclycerViewComic = view.findViewById(R.id.list_comic) as RecyclerView
        reclycerViewSeries = view.findViewById(R.id.list_series) as RecyclerView

        loadView()
        detailCharacterPresenterImpl.getMarvelCharacter(arguments.get(MarvelConstant.ID) as Int)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        activity.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun loadView() {
        comicsAdapter = DetailComicsAdapter()
        seriesAdapter = DetailSeriesAdapter()

        reclycerViewComic.adapter = comicsAdapter
        reclycerViewComic.setHasFixedSize(true)
        reclycerViewComic.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        reclycerViewSeries.adapter = seriesAdapter
        reclycerViewSeries.setHasFixedSize(true)
        reclycerViewSeries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun loadCharacter(marvelCharacter: MarvelCharacter) {
        MarvelUtil.setImageUrl(context, marvelCharacter.thumbMail?.getPathExtension(), image)
        title.text = marvelCharacter.name
        description.text = if (marvelCharacter.description?.length == 0) getString(R.string.no_description) else marvelCharacter.description
        appActivity.supportActionBar?.title = marvelCharacter.name

        marvelCharacter.comicList?.let {
            comicsAdapter.setComics(it)
            blockComics.visibility = View.VISIBLE
        }
        marvelCharacter.seriesList?.let {
            seriesAdapter.setSeries(it)
            blockSeries.visibility = View.VISIBLE
        }
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun error() {
        val includeProblem = view?.findViewById(R.id.include_problem_screen)
        val imageProblem = view?.findViewById(R.id.image_problem) as ImageView
        val textProblem = view?.findViewById(R.id.text_problem) as TextView
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.deadpool_error)

        includeProblem?.visibility = View.VISIBLE
        imageProblem.setImageBitmap(MarvelUtil.blur(context, bitmap))
        textProblem.text = getString(R.string.problem_generic)
    }
}