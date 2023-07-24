package com.discutions.app.utils

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class Dialogs {
    fun showDialog(context:android.content.Context, title: String, message: String) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage(message)
            .setPositiveButton("Accept", DialogInterface.OnClickListener {
                    _, _ -> dialogBuilder.create().dismiss()
            });
        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }
}