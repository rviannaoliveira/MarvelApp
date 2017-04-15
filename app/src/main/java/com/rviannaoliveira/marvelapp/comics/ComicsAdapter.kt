package com.rviannaoliveira.marvelapp.comics

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
 * Criado por rodrigo on 14/04/17.
 */
class ComicsAdapter : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>() {
    private lateinit var context: Context
    private var comics = ArrayList<MarvelComic>()

    fun setComics(comics: ArrayList<MarvelComic>) {
        this.comics = comics
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        context = parent.context
        return ComicViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_row, parent, false))

    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        if (comics.isNotEmpty()) {
            val marvelComic = comics[position]
            holder.name.text = marvelComic.title
            MarvelUtil.setImageUrl(context, marvelComic.thumbMail.toString(), holder.image)
        }
    }


    override fun getItemCount(): Int {
        return comics.size
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var name = itemView.findViewById(R.id.name_item) as TextView
    }
}