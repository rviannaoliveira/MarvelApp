package com.rviannaoliveira.marvelapp.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
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

    fun isLand(context: Context): Boolean {
        val intOrientation = context.resources.configuration.orientation
        return Configuration.ORIENTATION_LANDSCAPE == intOrientation

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

    fun showErrorScreen(context: Context, view: View?, resources: Resources, @DrawableRes drawable: Int) {
        val includeProblem = view?.findViewById(R.id.include_problem_screen)
        val imageProblem = view?.findViewById(R.id.image_problem) as ImageView
        val textProblem = view.findViewById(R.id.text_problem) as TextView
        val bitmap = BitmapFactory.decodeResource(resources, drawable)

        includeProblem?.visibility = View.VISIBLE
        imageProblem.setImageBitmap(MarvelUtil.blur(context, bitmap))
        textProblem.text = context.getString(R.string.problem_generic)
        textProblem.setTextColor(ContextCompat.getColor(context, R.color.textColorPrimary))
    }

    fun getMetricsScreenList(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowsManager = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowsManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWidth = displayMetrics.widthPixels
        return deviceWidth - (deviceWidth / 100 * 10)
    }

    fun getHeightScreen(context: Context): Int {
        return getDisplayMetrics(context).heightPixels
    }

    fun getWidthScreen(context: Context): Int {
        return getDisplayMetrics(context).widthPixels
    }


    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

}