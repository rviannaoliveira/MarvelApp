package com.rviannaoliveira.marvelapp.characters

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
import com.rviannaoliveira.marvelapp.detailCharacter.DetailCharacterActivity
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 08/04/17.
 */
class CharactersAdapter(private val presenter: CharactersPresenter, private val appCompatActivity: AppCompatActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BaseRecyclerView {
    private lateinit var context: Context
    private var characters = ArrayList<MarvelCharacter>()
    private var charactersOriginal = ArrayList<MarvelCharacter>()
    private var showLoader: Boolean = false
    private val VIEW_ITEM = 1
    private val VIEW_LOADER = 2
    private var listForLetter: Boolean = false

    override fun isListForLetter() = listForLetter

    fun setCharacters(characters: List<MarvelCharacter>, listForLetter: Boolean) {
        charactersOriginal.addAll(characters)
        this.characters.addAll(characters)
        this.listForLetter = listForLetter
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (!this.listForLetter &&
                characters.size == charactersOriginal.size &&
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
            return CharactersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))
        } else {
            return LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loader_item_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (characters.isNotEmpty()) {
            if (holder is CharactersViewHolder) {
                val marvelCharacter = characters[position]
                holder.name.text = marvelCharacter.name
                MarvelUtil.setImageUrl(context, marvelCharacter.thumbMail?.getPathExtension(), holder.image)
                holder.favorite.setButtonDrawable(toggleImage(marvelCharacter.favorite != null))
                holder.favorite.setOnClickListener { view -> toggleFavorite(position, view) }
                holder.image.setOnClickListener { showDetail(holder, marvelCharacter) }
            } else {
                if (showLoader) {
                    (holder as LoaderViewHolder).progressBar.visibility = View.VISIBLE
                } else {
                    (holder as LoaderViewHolder).progressBar.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun showDetail(holder: CharactersViewHolder, marvelCharacter: MarvelCharacter) {
        val detailIntent = Intent(context, DetailCharacterActivity::class.java)
        detailIntent.putExtra(KeyDatabase.ID, marvelCharacter.id)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imagePair = Pair.create(holder.image as View, holder.image.transitionName)
            val holderPair = Pair.create(holder.name as View, holder.name.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(appCompatActivity, imagePair, holderPair)
            ActivityCompat.startActivity(context, detailIntent, options.toBundle())
        } else {
            context.startActivity(detailIntent)
        }
    }

    override fun toggleFavorite(position: Int, view: View) {
        val checkView = view as CheckBox
        val character = characters[position]
        checkView.setButtonDrawable(toggleImage(checkView.isChecked))

        if (character.favorite == null) {
            character.favorite = Favorite()
            character.favorite?.groupType = KeyDatabase.FavoriteGroup.CHARACTERS
            character.favorite?.extension = character.thumbMail?.extension
            character.favorite?.path = character.thumbMail?.path
            character.favorite?.name = character.name
            character.favorite?.idMarvel = character.id
        }

        character.favorite?.let { presenter.toggleFavorite(it, checkView.isChecked) }

    }

    override fun toggleImage(checked: Boolean): Int {
        return if (checked) R.drawable.ic_star_white_24px else R.drawable.ic_star_border_white_24px
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun showLoading(status: Boolean) {
        showLoader = status
    }

    override fun filter(text: String?) {
        characters.clear()
        text?.let {
            if (it.isEmpty()) {
                characters.addAll(charactersOriginal)
            } else {
                charactersOriginal
                        .filter { it.name != null && it.name.toString().contains(text, true) }
                        .map { characters.add(it) }

            }
            notifyDataSetChanged()
        }
    }

    override fun clear() {
        characters.clear()
        charactersOriginal.clear()
        notifyDataSetChanged()
    }

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.image_item) as ImageView
        var name = itemView.findViewById<TextView>(R.id.name_item) as TextView
        var favorite = itemView.findViewById<CheckBox>(R.id.check_favorite) as CheckBox
    }

    inner class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar = itemView.findViewById<ProgressBar>(R.id.progressbar) as ProgressBar
    }
}