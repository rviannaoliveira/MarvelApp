package com.rviannaoliveira.marvelapp.detailCharacter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.MarvelComic
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rcalixto on 09/05/2017.
 */

class DetailComicsAdapter : RecyclerView.Adapter<DetailComicsAdapter.DetailComicViewHolder>() {
    private lateinit var context: Context
    private lateinit var comics: ArrayList<MarvelComic>

    fun setComics(comics: ArrayList<MarvelComic>) {
        this.comics = comics
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailComicViewHolder {
        this.context = parent.context
        return DetailComicViewHolder(LayoutInflater.from(context).inflate(R.layout.item_subdetail, parent, false))
    }

    override fun onBindViewHolder(holder: DetailComicViewHolder, position: Int) {
        if (comics.isNotEmpty()) {
            val marvelComic = comics[position]
            holder.name.text = marvelComic.title
            MarvelUtil.setImageUrl(context, marvelComic.thumbMail?.getPathExtension(), holder.image)
        }
    }

    override fun getItemCount(): Int = comics.size

    inner class DetailComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.image_item_subdetail) as ImageView
        var name = itemView.findViewById<TextView>(R.id.text_item_subdetail) as TextView
    }
}
