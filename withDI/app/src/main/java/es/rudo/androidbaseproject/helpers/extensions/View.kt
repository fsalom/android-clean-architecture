package es.rudo.androidbaseproject.helpers.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

fun View.showWithAnimation() {
    visibility = View.VISIBLE
    alpha = 0.0f
    animate().alpha(1.0f).setListener(null)
}

fun View.hideWithAnimation() {
    animate().alpha(0.0f).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            visibility = View.GONE
        }
    })
}