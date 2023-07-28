package com.discutions.app.utils

import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.discutions.app.R

class GenericToast {

   companion object{
       fun showToast(context: Context, message:String, isSuccess:Boolean){
           val toast = Toast(context);
           toast.apply {
               val layout = LayoutInflater.from(context).inflate(R.layout.toast, null)
               layout.findViewById<TextView>(R.id.toastText).text = message
               if (isSuccess)layout.setBackgroundResource(R.drawable.toast_success_style)
               else layout.setBackgroundResource(R.drawable.toast_fail_style)
               setGravity(Gravity.CENTER,0,0);
               layout.setOnClickListener {
                   toast.cancel();
               }
               view = layout
               show();
           }
           Handler().postDelayed({
               toast.cancel()
           }, 1000)
       }
   }
}