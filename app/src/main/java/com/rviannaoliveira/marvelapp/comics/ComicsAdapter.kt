package com.rviannaoliveira.marvelapp.comics

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.base.BaseRecyclerView
import com.rviannaoliveira.marvelapp.detailComic.DetailComicActivity
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.FavoriteGroup
import com.rviannaoliveira.marvelapp.model.MarvelComic
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 14/04/17.
 */
class ComicsAdapter(private val presenter: ComicsPresenter, private val appCompatActivity: AppCompatActivity) : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>(), BaseRecyclerView {
    private lateinit var context: Context
    private var comics = ArrayList<MarvelComic>()

    fun setComics(comics: ArrayList<MarvelComic>) {
        this.comics.addAll(comics)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        context = parent.context
        return ComicViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row, parent, false))

    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        if (comics.isNotEmpty()) {
            val marvelComic = comics[position]
            holder.name.text = marvelComic.title
            MarvelUtil.setImageUrl(context, marvelComic.thumbMail?.getPathExtension(), holder.image)
            holder.favorite.setButtonDrawable(toggleImage(marvelComic.favorite != null))
            holder.favorite.setOnClickListener { view -> toggleFavorite(position, view) }
            holder.image.setOnClickListener { showDetail(holder, marvelComic) }

        }
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun toggleFavorite(position: Int, view: View) {
        val checkView = view as CheckBox
        val comics = comics[position]
        checkView.setButtonDrawable(toggleImage(checkView.isChecked))

        if (comics.favorite == null) {
            comics.favorite = Favorite()
            comics.favorite?.group = FavoriteGroup.COMICS
            comics.favorite?.extension = comics.thumbMail?.extension
            comics.favorite?.path = comics.thumbMail?.path
            comics.favorite?.name = comics.title
            comics.favorite?.idMarvel = comics.id
        }
        comics.favorite?.let { presenter.toggleFavorite(it, checkView.isChecked) }
    }

    override fun toggleImage(checked: Boolean): Int {
        return if (checked) R.drawable.ic_star_white_24px else R.drawable.ic_star_border_white_24px
    }

    private fun showDetail(holder: ComicsAdapter.ComicViewHolder, marvelComic: MarvelComic) {
        val detailIntent = Intent(context, DetailComicActivity::class.java)
        detailIntent.putExtra(MarvelConstant.ID, marvelComic.id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imagePair = Pair.create(holder.image as View, holder.image.transitionName)
            val holderPair = Pair.create(holder.name as View, holder.name.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(appCompatActivity, imagePair, holderPair)
            ActivityCompat.startActivity(context, detailIntent, options.toBundle())
        } else {
            context.startActivity(detailIntent)
        }
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var name = itemView.findViewById(R.id.name_item) as TextView
        var favorite = itemView.findViewById(R.id.check_favorite) as CheckBox
    }
}