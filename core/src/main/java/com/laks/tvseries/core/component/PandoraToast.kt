package com.laks.tvseries.core.component

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.laks.tvseries.core.R

object PandoraToast {
    fun showRegularMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSuccessMessage(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val toastContentView = layoutInflater.inflate(R.layout.layout_pandora_toast, null)!!
        var labelMessage = toastContentView.findViewById<TextView>(R.id.labelMessage)
        labelMessage.text = message
        toast.view = toastContentView
        toast.show()
    }

    fun showErrorMessage(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val toastContentView = layoutInflater.inflate(R.layout.layout_pandora_toast, null)!!
        var iconDrawable = toastContentView.findViewById<ImageView>(R.id.imageIcon)
        var labelMessage = toastContentView.findViewById<TextView>(R.id.labelMessage)
        iconDrawable.setBackgroundResource(R.drawable.ic_toast_error)
        labelMessage.text = message
        toast.view = toastContentView
        toast.show()
    }
}