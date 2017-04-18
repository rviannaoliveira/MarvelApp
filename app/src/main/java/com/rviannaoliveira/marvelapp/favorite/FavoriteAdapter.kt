package com.rviannaoliveira.marvelapp.favorite

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
class FavoriteAdapter(private val favoritePresenterImpl: FavoritePresenter) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private lateinit var context: Context
    private var favorites = ArrayList<Favorite>()

    fun setFavorites(favorites: List<Favorite>) {
        this.favorites.addAll(favorites)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        this.context = parent.context
        return FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_row, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (favorites.isNotEmpty()) {
            val favorite = favorites[position]
            holder.name.text = favorite.name
            MarvelUtil.setImageUrl(context, favorite.getThumbMail(), holder.image)
            holder.delete.setOnClickListener {
                AlertDialog.Builder(context).setIcon(R.drawable.ic_spiderman)
                        .setTitle(context.getString(R.string.warning))
                        .setMessage(R.string.txt_delete)
                        .setPositiveButton(context.getString(R.string.ok), { _: DialogInterface, _: Int -> removeFavorite(favorite, position) })
                        .setNegativeButton(context.getString(R.string.cancel), null)
                        .show()
            }
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun clear() {
        favorites.clear()
        notifyDataSetChanged()
    }

    private fun removeFavorite(favorite: Favorite, position: Int) {
        favorites.remove(favorite)
        favoritePresenterImpl.deleteFavorite(favorite)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var name = itemView.findViewById(R.id.name_item) as TextView
        var delete = itemView.findViewById(R.id.delete_favorite) as ImageView
    }
}