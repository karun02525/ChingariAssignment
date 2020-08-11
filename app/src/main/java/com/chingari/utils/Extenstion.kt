package com.chingari.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.chingari.R
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showSnack(message: String): Snackbar {
    val sb = Snackbar.make((this as Activity).findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG)
    sb.view.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    val textView = sb.view.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(Color.RED)
    sb.show()
    return sb
}


fun Long.dateFormatStyle():String = this.finalDayFormatStyle()

@SuppressLint("SimpleDateFormat")
fun Long.finalDayFormatStyle():String{
    val format2: DateFormat = SimpleDateFormat("EEEE")
    return format2.format(Date(this * 1000))
}

@SuppressLint("SimpleDateFormat")
fun Long.dateStyle():String{
    return SimpleDateFormat("dd/MM/yyy", Locale.ENGLISH).format(Date(this * 1000))
}





fun Long.dayFormat():String = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(this*1000))


