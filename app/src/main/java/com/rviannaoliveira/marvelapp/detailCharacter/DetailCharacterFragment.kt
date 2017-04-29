package com.rviannaoliveira.marvelapp.characters

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
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
import com.rviannaoliveira.marvelapp.detailCharacter.MarvelPagerAdapter
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
    private lateinit var viewPagerComic: ViewPager
    private lateinit var blockComics: LinearLayout


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_character, container, false)
        appActivity = activity as AppCompatActivity
        appActivity.title = ""
        setHasOptionsMenu(true)

        image = view?.findViewById(R.id.image) as ImageView
        description = view.findViewById(R.id.description) as TextView
        progressbar = view.findViewById(R.id.progressbar) as ProgressBar
        title = view.findViewById(R.id.title) as TextView
        viewPagerComic = view.findViewById(R.id.view_pager_comics) as ViewPager
        blockComics = view.findViewById(R.id.block_pager_view_comics) as LinearLayout
        detailCharacterPresenterImpl.getMarvelCharacter(arguments.get(MarvelConstant.ID) as Int)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        activity.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun loadView() {
    }

    override fun loadCharacter(marvelCharacter: MarvelCharacter) {
        MarvelUtil.setImageUrl(context, marvelCharacter.thumbMail?.getPathExtension(), image)
        title.text = marvelCharacter.name
        description.text = if (marvelCharacter.description?.length == 0) getString(R.string.no_description) else marvelCharacter.description
        appActivity.supportActionBar?.title = marvelCharacter.name
        marvelCharacter.comicList?.let {
            viewPagerComic.adapter = MarvelPagerAdapter(context, marvelCharacter.comicList!!)

            if (viewPagerComic.adapter.count > 0) {
                viewPagerComic.clipToPadding = false
                viewPagerComic.setPadding(0, 0, 50, 0)
                viewPagerComic.pageMargin = 80
                viewPagerComic.offscreenPageLimit = 2
                blockComics.visibility = View.VISIBLE
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
        val includeProblem = view?.findViewById(R.id.include_problem_screen)
        val imageProblem = view?.findViewById(R.id.image_problem) as ImageView
        val textProblem = view?.findViewById(R.id.text_problem) as TextView
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.deadpool_error)

        includeProblem?.visibility = View.VISIBLE
        imageProblem.setImageBitmap(MarvelUtil.blur(context, bitmap))
        textProblem.text = getString(R.string.problem_generic)
    }
}