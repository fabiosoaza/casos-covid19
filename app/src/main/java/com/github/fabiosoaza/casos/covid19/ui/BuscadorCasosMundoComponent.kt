package com.github.fabiosoaza.casos.covid19.ui

import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.github.fabiosoaza.casos.covid19.R
import com.github.fabiosoaza.casos.covid19.domain.CasosCovid19
import com.github.fabiosoaza.casos.covid19.util.CasosCovidUtil
import com.github.fabiosoaza.casos.covid19.util.UiUtil
import com.github.fabiosoaza.casos.covid19.webservice.facade.Covid19Facade
import java.net.SocketTimeoutException

class BuscadorCasosMundoComponent(private var view: ComponentActivity, private var facade: Covid19Facade, private var barraProgresso:ProgressBarComponent) : BuscadorCasosComponent{

    private val viewTotalConfirmed: TextView = view.findViewById<TextView>(R.id.txtTotalConfirmedWorld)
    private val viewTotalRecovered: TextView = view.findViewById<TextView>(R.id.txtTotalRecoveredWorld)
    private val viewTotalDeaths: TextView = view.findViewById<TextView>(R.id.txtTotalDeathsWorld)
    private val viewGroupSummaryWorld = view.findViewById<View>(R.id.viewGroupSummaryWorld)





    override fun buscar() : List<CasosCovid19>{
        if (!CasosCovidUtil.hasConnection(view)) {
            UiUtil.message(view,  view.getString(R.string.noInternetPermissionWarning))
            return mutableListOf<CasosCovid19>()
        }

        return try {
            facade.listarCasosMundo()
        }catch(ex: SocketTimeoutException ){
            UiUtil.message(view,  view.getString(R.string.cantConnectToHost))
            mutableListOf<CasosCovid19>()
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

    override fun atualizarResultados(casos: List<CasosCovid19>){
        atualizarTotaisMundo(casos)
    }

    private fun atualizarTotaisMundo(casos: List<CasosCovid19>) {
        val total = CasosCovidUtil.somarCasos(casos,  view.getString(R.string.labelWorld))
        updateTextViewCounter(viewTotalConfirmed, total?.confirmed)
        updateTextViewCounter(viewTotalRecovered, total?.recovered)
        updateTextViewCounter(viewTotalDeaths, total?.deaths)
        CasosCovidUtil.updateContentDecriptionSummary(
            view.baseContext,
            viewGroupSummaryWorld,
            total,
            view.baseContext.getString(R.string.contentDescriptionTotalCasesSummary)
        )
    }



    private fun updateTextViewCounter(viewTotalConfirmed1: TextView, confirmed: Int? ) {
        viewTotalConfirmed1.text = confirmed?.toString() ?: "-"
    }


}