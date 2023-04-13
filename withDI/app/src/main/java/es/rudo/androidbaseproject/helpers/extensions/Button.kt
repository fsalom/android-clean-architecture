package es.rudo.androidbaseproject.helpers.extensions

import android.graphics.Color
import android.widget.Button

fun Button.disableButton(color: Int? = null) {
    alpha = 0.5f
    isEnabled = false
    color?.let { setBackgroundColor(it) }
}

fun Button.disableButton(color: String? = null) {
    alpha = 0.5f
    isEnabled = false
    color?.let { setBackgroundColor(Color.parseColor(it)) }
}

fun Button.enableButton(color: Int? = null) {
    alpha = 1.0f
    isEnabled = true
    color?.let { setBackgroundColor(it) }
}

fun Button.enableButton(color: String? = null) {
    alpha = 1.0f
    isEnabled = true
    color?.let { setBackgroundColor(Color.parseColor(it)) }
}