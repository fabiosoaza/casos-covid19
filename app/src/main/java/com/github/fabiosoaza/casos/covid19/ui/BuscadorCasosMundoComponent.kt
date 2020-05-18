package com.github.fabiosoaza.casos.covid19.ui

import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.fabiosoaza.casos.covid19.R
import com.github.fabiosoaza.casos.covid19.domain.Casos
import com.github.fabiosoaza.casos.covid19.util.CasosCovidUtil
import com.github.fabiosoaza.casos.covid19.util.UiUtil
import com.github.fabiosoaza.casos.covid19.webservice.facade.Covid19Facade
import java.net.SocketTimeoutException

class BuscadorCasosMundoComponent(private var view: ComponentActivity, private var facade: Covid19Facade, private var barraProgresso:ProgressBarComponent) : BuscadorCasosComponent{

    private val viewTotalConfirmed: TextView = view?.findViewById<TextView>(R.id.txtTotalConfirmedWorld)
    private val viewTotalRecovered: TextView = view?.findViewById<TextView>(R.id.txtTotalRecoveredWorld)
    private val viewTotalDeaths: TextView = view?.findViewById<TextView>(R.id.txtTotalDeathsWorld)



    override fun buscar() : List<Casos>{
        if (!CasosCovidUtil.hasConnection(view)) {
            UiUtil.message(view,  view.getString(R.string.noInternetPermissionWarning))
            return mutableListOf<Casos>()
        }

        return try {
            facade.listarCasosMundo()
        }catch(ex: SocketTimeoutException ){
            UiUtil.message(view,  view.getString(R.string.cantConnectToHost))
            mutableListOf<Casos>()
        }
    }

    override fun showProgress(show: Boolean) {
        if(show){
            barraProgresso.show()
        }
        else{
            barraProgresso.hide()
        }

    }

    override fun atualizarResultados(casos: List<Casos>){
        atualizarTotaisMundo(casos)
    }

    private fun atualizarTotaisMundo(casos: List<Casos>) {
        val total = CasosCovidUtil.somarCasos(casos,  view.getString(R.string.labelWorld))
        updateTextViewCounter(viewTotalConfirmed, total?.confirmed)
        updateTextViewCounter(viewTotalRecovered, total?.recovered)
        updateTextViewCounter(viewTotalDeaths, total?.deaths)
        CasosCovidUtil.updateContentDecriptionSummary(
            view.baseContext,
            viewTotalConfirmed,
            total,
            view.baseContext.getString(R.string.contentDescriptionTotalCasesSummary)
        )
    }



    private fun updateTextViewCounter(viewTotalConfirmed1: TextView, confirmed: Int? ) {
        viewTotalConfirmed1.text = confirmed?.toString() ?: "-"
    }


}