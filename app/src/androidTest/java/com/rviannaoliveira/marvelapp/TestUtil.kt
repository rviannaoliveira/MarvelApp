package com.rviannaoliveira.marvelapp

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import io.realm.Realm
import io.realm.RealmConfiguration
import org.hamcrest.Matcher


/**
 * Criado por rodrigo on 02/07/17.
 */

object TestUtil {

    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById(id)
                v.performClick()
            }
        }
    }

    fun initRealmTest(): Realm {
        val testConfig = RealmConfiguration.Builder().inMemory().name("class_Favorite").build()
        return Realm.getInstance(testConfig)
    }
}
