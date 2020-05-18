package com.github.fabiosoaza.casos.covid19.ui

import android.view.View
import androidx.activity.ComponentActivity
import com.github.fabiosoaza.casos.covid19.R
import com.github.fabiosoaza.casos.covid19.util.UiUtil
import java.util.concurrent.atomic.AtomicBoolean

class ProgressBarComponent (private var view: ComponentActivity) {

    var progressOverlay: View = view.findViewById(R.id.progressOverlay)
    var showingProgressBar : AtomicBoolean = AtomicBoolean(false)

    fun show(){
        if(showingProgressBar.compareAndSet(false, true)){
            UiUtil.sendTalkBackMessage(view.baseContext, view.getString(R.string.labelDownloadingData))
            UiUtil.animateView(progressOverlay, View.VISIBLE, 0.4F, 200)
        }
    }

    fun hide(){
        if(showingProgressBar.compareAndSet(true, false)) {
            UiUtil.sendTalkBackMessage(view.baseContext, view.getString(R.string.labelDownloadingDataComplete))
            UiUtil.animateView(progressOverlay, View.GONE, 0F, 200)
        }
    }
}