package com.rviannaoliveira.marvelapp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.characters.CharactersFragment
import com.rviannaoliveira.marvelapp.util.MarvelUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MarvelUtil.showFragment(this, R.id.content_main, CharactersFragment(), false)
    }
}
