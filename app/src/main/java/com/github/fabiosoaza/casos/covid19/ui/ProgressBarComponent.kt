package com.github.fabiosoaza.casos.covid19.ui

import android.view.View
import androidx.activity.ComponentActivity
import com.github.fabiosoaza.casos.covid19.R
import com.github.fabiosoaza.casos.covid19.util.UiUtil

class ProgressBarComponent (private var view: ComponentActivity) {

    var progressOverlay: View = view.findViewById(R.id.progressOverlay)

    fun show(){
        UiUtil.animateView(progressOverlay, View.VISIBLE, 0.4F, 200);
    }

    fun hide(){
        UiUtil.animateView(progressOverlay, View.GONE, 0F, 200);
    }
}