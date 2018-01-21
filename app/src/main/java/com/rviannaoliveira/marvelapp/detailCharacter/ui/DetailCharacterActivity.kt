package com.rviannaoliveira.marvelapp.detailCharacter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.base.BaseActivity
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import com.rviannaoliveira.marvelapp.util.MarvelUtil

class DetailCharacterActivity : BaseActivity() {

    // Problem kodein version 4.1.0
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val bundle = this.intent.extras
        val detailFragment = DetailCharacterFragment()
        detailFragment.arguments = bundle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient_top))

        if (savedInstanceState == null) {
            MarvelUtil.showFragment(this, R.id.content_main, detailFragment, false, MarvelConstant.CHARACTER)
        }

    }
}
