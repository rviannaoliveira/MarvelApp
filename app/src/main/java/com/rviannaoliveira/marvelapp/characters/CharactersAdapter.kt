package com.rviannaoliveira.marvelapp.characters

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
import java.util.*

/**
 * Criado por rodrigo on 08/04/17.
 */
class CharactersAdapter(private val characters: ArrayList<MarvelCharacter>) : RecyclerView.Adapter<CharactersAdapter.MarvelViewHolder>() {
    private lateinit var context: Context


    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        if (characters.isNotEmpty()) {
            val marvelCharacter = characters[position]
            holder.name.text = marvelCharacter.name
            MarvelUtil.setImageUrl(context, marvelCharacter.thumbMail.toString(), holder.image, 90, 90)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        this.context = parent.context
        return MarvelViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_row, parent, false))
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun clear() {
        characters.clear()
        notifyDataSetChanged()
    }

    inner class MarvelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var name = itemView.findViewById(R.id.name_item) as TextView
    }


}