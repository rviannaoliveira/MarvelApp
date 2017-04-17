package com.rviannaoliveira.marvelapp.favorite

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 15/04/17.
 */
class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var context: Context
    private var favorites = ArrayList<Favorite>()

    fun setFavorites(favorites: ArrayList<Favorite>) {
        this.favorites = favorites
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        this.context = parent.context
        return FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_row, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (favorites.isNotEmpty()) {
            val favorite = favorites[position]
            holder.name.text = favorite.name
            MarvelUtil.setImageUrl(context, favorite.getThumbMail(), holder.image)
        }
    }


    override fun getItemCount(): Int {
        return favorites.size
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var name = itemView.findViewById(R.id.name_item) as TextView
    }
}