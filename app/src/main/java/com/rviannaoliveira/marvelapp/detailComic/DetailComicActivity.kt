package com.rviannaoliveira.marvelapp.detailComic

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.base.BaseActivity
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import com.rviannaoliveira.marvelapp.util.MarvelUtil

/**
 * Criado por rodrigo on 13/05/17.
 */
class DetailComicActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val bundle = this.intent.extras
        val detailFragment = DetailComicFragment()
        detailFragment.arguments = bundle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient_top))

        if (savedInstanceState == null) {
            MarvelUtil.showFragment(this, R.id.content_main, detailFragment, false, MarvelConstant.CHARACTER)
        }

    }
}
