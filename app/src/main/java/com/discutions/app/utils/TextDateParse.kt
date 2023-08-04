package com.discutions.app.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class TextDateParse {
    companion object{
        fun toTextDate(date: Timestamp): String {
            return try {
                SimpleDateFormat("dd MMMM yyyy hh:mm a", Locale.US).format(date.toDate())
            }catch (e:Exception){

            }.toString()
        }
    }
}