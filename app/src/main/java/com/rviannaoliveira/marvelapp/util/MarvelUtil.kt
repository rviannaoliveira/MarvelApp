package com.rviannaoliveira.marvelapp.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.widget.ImageView
import com.rviannaoliveira.marvelapp.R
import com.squareup.picasso.Picasso

/**
 * Criado por rodrigo on 09/04/17.
 */
object MarvelUtil {

    fun setImageUrl(context: Context, url: String?, image: ImageView) {
        Picasso.with(context)
                .load(url)
                .fit()
                .error(R.drawable.image_broken)
                .placeholder(R.drawable.ic_marvel_comics)
                .into(image)
    }

    fun showFragment(context: AppCompatActivity, id: Int, fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val fragmentManager = context.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(id, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    fun isPortrait(context: Context): Boolean {
        val intOrientation = context.resources.configuration.orientation
        return Configuration.ORIENTATION_PORTRAIT == intOrientation

    }

    fun convertDpToPixel(dp: Int, context: Context): Int {
        val metrics = context.resources.displayMetrics
        return (dp * Math.round((metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT).toDouble())).toInt()
    }


    fun blur(context: Context, image: Bitmap): Bitmap {
        val BITMAP_SCALE = 0.3f
        val BLUR_RADIUS = 7.5f
        val width = Math.round(image.width * BITMAP_SCALE)
        val height = Math.round(image.height * BITMAP_SCALE)

        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(BLUR_RADIUS)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }

}