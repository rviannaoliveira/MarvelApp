package com.rviannaoliveira.marvelapp.main

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rviannaoliveira.marvelapp.R
import com.rviannaoliveira.marvelapp.util.MarvelUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        MarvelUtil.showFragment(this, R.id.content_main, CharactersFragment(), false)
        startSplash()
    }

    private fun startSplash() {
        val splashView = findViewById(R.id.splash)
        splashView.visibility = View.VISIBLE

        Handler().postDelayed({
            splashView.visibility = View.GONE
        }, resources.getInteger(R.integer.delay_splash).toLong())
    }
}
