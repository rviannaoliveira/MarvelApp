package com.rviannaoliveira.marvelapp.favorite

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.data.repository.KeyDatabase
import com.rviannaoliveira.marvelapp.detailCharacter.ui.DetailCharacterActivity
import com.rviannaoliveira.marvelapp.detailComic.DetailComicActivity
import com.rviannaoliveira.marvelapp.model.Favorite
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 15/04/17.
 */
class FavoriteAdapter(private val appCompatActivity: AppCompatActivity, private val favoritePresenterImpl: FavoritePresenter) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private lateinit var context: Context
    private var favorites = ArrayList<Favorite>()
    private lateinit var block: LinearLayout

    fun setFavorites(favorites: List<Favorite>, block: LinearLayout) {
        this.favorites.clear()
        this.favorites.addAll(favorites)
        this.block = block
        if (favorites.isNotEmpty()) {
            block.visibility = View.VISIBLE
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        this.context = parent.context
        return FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_row, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        if (favorites.isNotEmpty()) {
            val favorite = favorites[position]
            holder.row.maxWidth = MarvelUtil.getMetricsScreenList(context)
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
            holder.image.setOnClickListener { showDetail(holder, favorite) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showDetail(holder: FavoriteViewHolder, favorite: Favorite) {
        val detailIntent = if (KeyDatabase.FavoriteGroup.CHARACTERS == favorite.groupType) Intent(context, DetailCharacterActivity::class.java) else Intent(context, DetailComicActivity::class.java)
        detailIntent.putExtra(KeyDatabase.ID, favorite.idMarvel)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imagePair = Pair.create(holder.image as View, holder.image.transitionName)
            val holderPair = Pair.create(holder.name as View, holder.name.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(appCompatActivity, imagePair, holderPair)
            ActivityCompat.startActivity(context, detailIntent, options.toBundle())
        } else {
            context.startActivity(detailIntent)
        }
    }
    override fun getItemCount(): Int {
        return favorites.size
    }

    private fun removeFavorite(favorite: Favorite, position: Int) {
        favoritePresenterImpl.deleteFavorite(favorite, block.id == R.id.block_character)
        favorites.remove(favorite)
        notifyItemRemoved(position)
        notifyDataSetChanged()

        if (favorites.size == 0) {
            block.visibility = View.GONE
        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var name = itemView.findViewById(R.id.name_item) as TextView
        var delete = itemView.findViewById(R.id.delete_favorite) as ImageView
        var row = itemView.findViewById(R.id.row) as ConstraintLayout
    }
}