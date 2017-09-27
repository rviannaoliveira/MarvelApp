package com.rviannaoliveira.marvelapp.detailCharacter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.MarvelCharacter
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rcalixto on 09/05/2017.
 */

class DetailCharacterAdapter : RecyclerView.Adapter<DetailCharacterAdapter.DetailCharacterViewHolder>() {
    private lateinit var context: Context
    private lateinit var character: ArrayList<MarvelCharacter>

    fun setCharacter(character: ArrayList<MarvelCharacter>) {
        this.character = character
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCharacterViewHolder {
        this.context = parent.context
        return DetailCharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_subdetail, parent, false))
    }

    override fun onBindViewHolder(holder: DetailCharacterViewHolder, position: Int) {
        if (character.isNotEmpty()) {
            val marvelCharacter = character[position]
            holder.name.text = marvelCharacter.name
            MarvelUtil.setImageUrl(context, marvelCharacter.thumbMail?.getPathExtension(), holder.image)
        }
    }

    override fun getItemCount(): Int = character.size

    inner class DetailCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.image_item_subdetail) as ImageView
        var name = itemView.findViewById<TextView>(R.id.text_item_subdetail) as TextView
    }
}
