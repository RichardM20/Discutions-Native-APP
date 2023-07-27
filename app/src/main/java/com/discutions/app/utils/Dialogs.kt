package com.discutions.app.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.discutions.app.R

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
class LoadingDialog{
    private var context: Context;
    private  var dialog: Dialog

    constructor(context: Context, dialog: Dialog) {
        this.context=context;
        this.dialog=dialog;
    }
    fun showLoadingDialog(title: String){
        dialog = Dialog(context);
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var loadingText = dialog.findViewById<TextView>(R.id.loadingText);
        loadingText.text = title;
        dialog.create();
        dialog.show();
    }

    fun hideLoadingDialog(){
        dialog.dismiss();
    }

}