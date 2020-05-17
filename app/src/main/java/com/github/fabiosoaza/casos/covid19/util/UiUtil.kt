package com.github.fabiosoaza.casos.covid19.util

import android.animation.Animator

import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.fabiosoaza.casos.covid19.R


class UiUtil {

    companion object {
        fun animateView(view: View, toVisibility: Int, toAlpha: Float, duration: Int) {
            val show = toVisibility == View.VISIBLE
            if (show) {
                view.bringToFront()
                view.alpha = 0F
            }
            view.visibility = View.VISIBLE
            view.animate()
                .setDuration(duration.toLong())
                .alpha((if (show) toAlpha else 0F))
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = toVisibility
                    }
                })
        }

        fun message(view: ComponentActivity, message: String) {
            view.runOnUiThread {
                Toast.makeText(
                    view,
                    message,
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }

}