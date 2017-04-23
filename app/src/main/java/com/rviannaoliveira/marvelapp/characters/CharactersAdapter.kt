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
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.base.BaseRecyclerView
import com.rviannaoliveira.marvelapp.detailCharacter.DetailCharacterActivity
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.model.FavoriteGroup
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 08/04/17.
 */
class CharactersAdapter(private val presenter: CharactersPresenter, private val appCompatActivity: AppCompatActivity) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>(), BaseRecyclerView {
    private lateinit var context: Context
    private var characters = ArrayList<MarvelCharacter>()

    fun setCharacters(characters: ArrayList<MarvelCharacter>) {
        this.characters = characters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        this.context = parent.context
        return CharactersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        if (characters.isNotEmpty()) {
            val marvelCharacter = characters[position]
            holder.name.text = marvelCharacter.name
            MarvelUtil.setImageUrl(context, marvelCharacter.thumbMail?.getPathExtension(), holder.image)
            holder.favorite.setButtonDrawable(toggleImage(marvelCharacter.favorite != null))
            holder.favorite.setOnClickListener { view -> toggleFavorite(position, view) }
            holder.image.setOnClickListener { showDetail(holder, marvelCharacter) }
        }

    }

    private fun showDetail(holder: CharactersViewHolder, marvelCharacter: MarvelCharacter) {
        val detailIntent = Intent(context, DetailCharacterActivity::class.java)
        detailIntent.putExtra(MarvelConstant.ID, marvelCharacter.id)

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
            character.favorite?.group = FavoriteGroup.CHARACTERS
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

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var name = itemView.findViewById(R.id.name_item) as TextView
        var favorite = itemView.findViewById(R.id.check_favorite) as CheckBox
    }


}