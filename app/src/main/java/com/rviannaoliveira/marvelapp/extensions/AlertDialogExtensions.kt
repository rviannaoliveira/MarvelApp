package com.rviannaoliveira.marvelapp.extensions

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.rviannaoliveira.marvelapp.R

/**
 * Criado por rodrigo on 24/06/17.
 */

fun AlertDialog.Builder.createFilterCustom(context: Context, listenerChoice: DialogInterface.OnClickListener, listener: DialogInterface.OnClickListener): AlertDialog {
    val alertDialog = this.setTitle(R.string.filter)
            .setSingleChoiceItems(context.resources.getStringArray(R.array.alphabetic), 0, listenerChoice)
            .setNegativeButton(android.R.string.cancel, { dialog, _ -> dialog.cancel() })
            .setPositiveButton(android.R.string.ok, listener)
            .create()
    return alertDialog
}
