package com.rviannaoliveira.marvelapp.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.characters.CharactersFragment
import com.rviannaoliveira.marvelapp.comics.ComicsFragment
import com.rviannaoliveira.marvelapp.favorite.FavoriteFragment
import com.rviannaoliveira.marvelapp.util.MarvelUtil

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem -> onNavigationItemReselectedListener(menuItem) }
        MarvelUtil.showFragment(this, R.id.content_main, CharactersFragment(), false)
    }

    fun onNavigationItemReselectedListener(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_character -> MarvelUtil.showFragment(this, R.id.content_main, CharactersFragment(), false)
            R.id.action_comic -> MarvelUtil.showFragment(this, R.id.content_main, ComicsFragment(), false)
            R.id.action_favorite -> MarvelUtil.showFragment(this, R.id.content_main, FavoriteFragment(), false)
        }
        return true
    }
}
