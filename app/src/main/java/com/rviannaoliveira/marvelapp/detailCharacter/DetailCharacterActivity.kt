package com.rviannaoliveira.marvelapp.detailCharacter

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.characters.DetailCharacterFragment
import com.rviannaoliveira.marvelapp.util.MarvelConstant
import com.rviannaoliveira.marvelapp.util.MarvelUtil

class DetailCharacterActivity : AppCompatActivity() {


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
