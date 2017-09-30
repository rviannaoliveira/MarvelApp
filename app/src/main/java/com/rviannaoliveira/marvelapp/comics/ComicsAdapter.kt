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
import android.widget.ProgressBar
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.base.BaseRecyclerView
import com.rviannaoliveira.marvelapp.data.repository.KeyDatabase
import com.rviannaoliveira.marvelapp.detailComic.DetailComicActivity
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.MarvelComic
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 14/04/17.
 */
class ComicsAdapter(private val presenter: ComicsPresenter, private val appCompatActivity: AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BaseRecyclerView {
    private lateinit var context: Context
    private var comics = ArrayList<MarvelComic>()
    private var comicsOriginal = ArrayList<MarvelComic>()
    private val VIEW_ITEM = 1
    private val VIEW_LOADER = 2
    private var showLoader: Boolean = false
    private var listForLetter: Boolean = false

    override fun isListForLetter() = listForLetter

    fun setComics(comics: ArrayList<MarvelComic>, listForLetter: Boolean) {
        this.comics.addAll(comics)
        this.comicsOriginal.addAll(comics)
        this.listForLetter = listForLetter
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (!this.listForLetter &&
                comics.size == comicsOriginal.size &&
                position != 0 &&
                (MarvelUtil.isPortrait(appCompatActivity) && (position == itemCount - 1 || position == itemCount - 2)) ||
                (MarvelUtil.isLand(appCompatActivity) && (position == itemCount - 1 || position == itemCount - 2 || position == itemCount - 3))) {
            return VIEW_LOADER
        }

        return VIEW_ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        if (viewType == VIEW_ITEM) {
            return ComicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))
        } else {
            return LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loader_item_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (comics.isNotEmpty()) {
            if (holder is ComicsAdapter.ComicViewHolder) {
                val marvelCharacter = comics[position]
                holder.name.text = marvelCharacter.title
                MarvelUtil.setImageUrl(context, marvelCharacter.thumbMail?.getPathExtension(), holder.image)
                holder.favorite.setButtonDrawable(toggleImage(marvelCharacter.favorite != null))
                holder.favorite.setOnClickListener { view -> toggleFavorite(position, view) }
                holder.image.setOnClickListener { showDetail(holder, marvelCharacter) }
            } else {
                if (showLoader) {
                    (holder as ComicsAdapter.LoaderViewHolder).progressBar.visibility = View.VISIBLE
                } else {
                    (holder as ComicsAdapter.LoaderViewHolder).progressBar.visibility = View.VISIBLE
                }
            }
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
            comics.favorite?.group = KeyDatabase.FavoriteGroup.COMICS
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
        detailIntent.putExtra(KeyDatabase.ID, marvelComic.id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imagePair = Pair.create(holder.image as View, holder.image.transitionName)
            val holderPair = Pair.create(holder.name as View, holder.name.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(appCompatActivity, imagePair, holderPair)
            ActivityCompat.startActivity(context, detailIntent, options.toBundle())
        } else {
            context.startActivity(detailIntent)
        }
    }

    fun showLoading(status: Boolean) {
        showLoader = status
    }

    override fun clear() {
        comics.clear()
        comicsOriginal.clear()
        notifyDataSetChanged()
    }

    override fun filter(text: String?) {
        comics.clear()
        text?.let {
            if (it.isEmpty()) {
                comics.addAll(comicsOriginal)
            } else {
                comicsOriginal.filter { it.title != null && it.title.toString().contains(text, true) }
                        .map { comics.add(it) }
            }
            notifyDataSetChanged()
        }
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.image_item) as ImageView
        var name = itemView.findViewById<TextView>(R.id.name_item) as TextView
        var favorite = itemView.findViewById<CheckBox>(R.id.check_favorite) as CheckBox
    }

    inner class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar = itemView.findViewById<ProgressBar>(R.id.progressbar) as ProgressBar
    }
}