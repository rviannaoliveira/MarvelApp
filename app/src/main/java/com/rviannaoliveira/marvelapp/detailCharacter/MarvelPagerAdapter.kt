package com.rviannaoliveira.marvelapp.detailCharacter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.model.MarvelComic
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 22/04/17.
 */
class MarvelPagerAdapter(private val context: Context, private val comics: ArrayList<MarvelComic>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val comic = comics[position]
        val view = LayoutInflater.from(context).inflate(R.layout.pager_item, container, false)
        val image = view.findViewById(R.id.image_pager) as ImageView
        val text = view.findViewById(R.id.text_pager) as TextView
        MarvelUtil.setImageUrl(context, comic.thumbMail?.getPathExtension(), image)
        text.text = comic.title
        container?.addView(view)
        return view
    }

    override fun isViewFromObject(view: View?, any: Any?): Boolean = view == any

    override fun getCount(): Int = comics.size

    override fun destroyItem(container: ViewGroup?, position: Int, view: Any?) {
        container?.removeView(view as View)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return super.getPageTitle(position)
    }


}