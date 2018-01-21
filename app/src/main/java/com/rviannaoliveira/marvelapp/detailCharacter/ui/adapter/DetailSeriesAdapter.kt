package com.rviannaoliveira.marvelapp.detailCharacter.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.MarvelSeries
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rcalixto on 09/05/2017.
 */

class DetailSeriesAdapter : RecyclerView.Adapter<DetailSeriesAdapter.DetailSeriesViewHolder>() {
    private lateinit var context: Context
    private lateinit var series: ArrayList<MarvelSeries>

    fun setSeries(series: ArrayList<MarvelSeries>) {
        this.series = series
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailSeriesViewHolder {
        this.context = parent.context
        return DetailSeriesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_subdetail, parent, false))
    }

    override fun onBindViewHolder(holder: DetailSeriesViewHolder, position: Int) {
        if (series.isNotEmpty()) {
            val serie = series[position]
            holder.name.text = serie.title
            MarvelUtil.setImageUrl(context, serie.thumbMail?.getPathExtension(), holder.image)
        }
    }

    override fun getItemCount(): Int = series.size

    inner class DetailSeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item_subdetail) as ImageView
        var name = itemView.findViewById(R.id.text_item_subdetail) as TextView
    }
}
