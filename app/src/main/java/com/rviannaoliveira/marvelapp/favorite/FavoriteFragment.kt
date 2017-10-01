package com.rviannaoliveira.marvelapp.favorite

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.util.MarvelUtil


/**
 * Criado por rodrigo on 15/04/17.
 */
class FavoriteFragment : Fragment(), FavoriteView {

    private val favoritePresenterImpl: FavoritePresenter = FavoritePresenterImpl(this)
    private lateinit var characterFavoriteAdapter: FavoriteAdapter
    private lateinit var comicFavoriteAdapter: FavoriteAdapter
    private lateinit var progressbar: ProgressBar
    private lateinit var characterFavoriteRecyclerView: RecyclerView
    private lateinit var comicFavoriteRecyclerView: RecyclerView
    private lateinit var blockCharacter: LinearLayout
    private lateinit var blockComic: LinearLayout
    private lateinit var viewFavorite: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewFavorite = inflater?.inflate(R.layout.fragment_favorite, container, false) as View
        characterFavoriteRecyclerView = viewFavorite.findViewById<RecyclerView>(R.id.list_character) as RecyclerView
        comicFavoriteRecyclerView = viewFavorite.findViewById<RecyclerView>(R.id.list_comic) as RecyclerView
        progressbar = viewFavorite.findViewById<ProgressBar>(R.id.progressbar) as ProgressBar
        blockCharacter = viewFavorite.findViewById<LinearLayout>(R.id.block_character) as LinearLayout
        blockComic = viewFavorite.findViewById<LinearLayout>(R.id.block_comics) as LinearLayout
        loadView()
        favoritePresenterImpl.loadFavorites()
        return viewFavorite
    }

    override fun loadView() {
        characterFavoriteAdapter = FavoriteAdapter(activity as AppCompatActivity, favoritePresenterImpl)
        characterFavoriteRecyclerView.adapter = characterFavoriteAdapter
        characterFavoriteRecyclerView.setHasFixedSize(true)
        characterFavoriteRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        comicFavoriteAdapter = FavoriteAdapter(activity as AppCompatActivity, favoritePresenterImpl)
        comicFavoriteRecyclerView.adapter = comicFavoriteAdapter
        comicFavoriteRecyclerView.setHasFixedSize(true)
        comicFavoriteRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    override fun loadFavorite(favorites: Favorite) {
        characterFavoriteAdapter.setFavorites(favorites.characters, blockCharacter)
        comicFavoriteAdapter.setFavorites(favorites.comics, blockComic)

        if (favorites.comics.isEmpty() && favorites.characters.isEmpty()) {
            favoriteEmpty()
        }
    }

    override fun error() {
        val includeProblem = view?.findViewById<View>(R.id.include_problem_screen)
        val imageProblem = view?.findViewById<ImageView>(R.id.image_problem) as ImageView
        val textProblem = view?.findViewById<TextView>(R.id.text_problem) as TextView
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.captain_error)

        includeProblem?.visibility = View.VISIBLE
        imageProblem.setImageBitmap(bitmap)
        textProblem.text = getString(R.string.favorite_empty)
        textProblem.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))
    }

    private fun favoriteEmpty() {
        val includeProblem = viewFavorite.findViewById<View>(R.id.include_problem_screen)
        val imageProblem = viewFavorite.findViewById<ImageView>(R.id.image_problem) as ImageView
        val textProblem = viewFavorite.findViewById<TextView>(R.id.text_problem) as TextView
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.spiderman_empty)
        val resized = Bitmap.createScaledBitmap(bitmap, (MarvelUtil.getWidthScreen(context) * 0.8).toInt(), (MarvelUtil.getHeightScreen(context) * 0.8).toInt(), true)

        includeProblem?.visibility = View.VISIBLE
        imageProblem.setImageBitmap(resized)
        textProblem.text = getString(R.string.favorite_empty)
        textProblem.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))
    }

}