package com.rviannaoliveira.marvelapp.favorite

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
import com.rviannaoliveira.marvelapp.comics.FavoritePresenterImpl
import com.rviannaoliveira.marvelapp.model.Favorite

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
        characterFavoriteRecyclerView = viewFavorite.findViewById(R.id.list_character) as RecyclerView
        comicFavoriteRecyclerView = viewFavorite.findViewById(R.id.list_comic) as RecyclerView
        progressbar = viewFavorite.findViewById(R.id.progressbar) as ProgressBar
        blockCharacter = viewFavorite.findViewById(R.id.block_character) as LinearLayout
        blockComic = viewFavorite.findViewById(R.id.block_comics) as LinearLayout
        loadView()
        favoritePresenterImpl.getFavorites()
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
        val includeProblem = view?.findViewById(R.id.include_problem_screen)
        val imageProblem = view?.findViewById(R.id.image_problem) as ImageView
        val textProblem = view?.findViewById(R.id.text_problem) as TextView
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.captain_error)

        includeProblem?.visibility = View.VISIBLE
        imageProblem.setImageBitmap(bitmap)
        textProblem.text = getString(R.string.favorite_empty)
        textProblem.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))
    }

    private fun favoriteEmpty() {
        val includeProblem = viewFavorite.findViewById(R.id.include_problem_screen)
        val imageProblem = viewFavorite.findViewById(R.id.image_problem) as ImageView
        val textProblem = viewFavorite.findViewById(R.id.text_problem) as TextView
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.spiderman_empty)

        includeProblem?.visibility = View.VISIBLE
        imageProblem.setImageBitmap(bitmap)
        textProblem.text = getString(R.string.favorite_empty)
        textProblem.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))
    }

}