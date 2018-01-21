package com.rviannaoliveira.marvelapp.base

import android.annotation.SuppressLint
import android.os.Bundle
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : KodeinAppCompatActivity() {
    // Problem kodein version 4.1.0
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}