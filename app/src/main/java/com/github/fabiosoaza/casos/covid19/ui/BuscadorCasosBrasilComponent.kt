package com.github.fabiosoaza.casos.covid19.ui

import android.widget.TextView
import androidx.activity.ComponentActivity
import com.github.fabiosoaza.casos.covid19.R
import com.github.fabiosoaza.casos.covid19.domain.Casos
import com.github.fabiosoaza.casos.covid19.util.CasosCovidUtil
import com.github.fabiosoaza.casos.covid19.util.UiUtil
import com.github.fabiosoaza.casos.covid19.webservice.facade.Covid19Facade
import jrizani.jrspinner.JRSpinner
import java.net.SocketTimeoutException

class BuscadorCasosBrasilComponent(
    private var view: ComponentActivity,
    private var facade: Covid19Facade,
    private var adapter: CasosAdapter,
    private var barraProgresso: ProgressBarComponent
) : BuscadorCasosComponent {

    private val viewTotalConfirmed: TextView = view?.findViewById<TextView>(R.id.txtTotalConfirmed)
    private val viewTotalRecovered: TextView = view?.findViewById<TextView>(R.id.txtTotalRecovered)
    private val viewTotalDeaths: TextView = view?.findViewById<TextView>(R.id.txtTotalDeaths)
    private val viewLocalSelection: JRSpinner = view?.findViewById<JRSpinner>(R.id.JRSpinner)

    private val labelAllstates = view.getString(R.string.labelAllstates)


    override fun buscar(): List<Casos> {
        if (!CasosCovidUtil.hasConnection(view)) {
            UiUtil.message(view, view.getString(R.string.noInternetPermissionWarning))
            return mutableListOf<Casos>()
        }
        val localSelecionado = viewLocalSelection.text.toString()
        return try {
            if (localSelecionado == labelAllstates || localSelecionado == "") {
                listarTodosEstados()
            } else {
                buscarEstado(localSelecionado)
            }
        } catch (ex: SocketTimeoutException) {
            UiUtil.message(view, view.getString(R.string.cantConnectToHost))
            mutableListOf<Casos>()
        }

    }

    private fun buscarEstado(localSelecionado: String): MutableList<Casos> {
        val list = mutableListOf<Casos>()
        val element = facade.buscarCasosEstado(localSelecionado.toLowerCase())
        if (element != null) {
            list.add(element)
        }
        return list
    }

    private fun listarTodosEstados(): List<Casos> {
        return facade.listarCasosTodosEstados()
    }


    override fun showProgress(show: Boolean) {
        if (show) {
            barraProgresso.show()
        } else {
            barraProgresso.hide()
        }

    }

    override fun atualizarResultados(casos: List<Casos>) {
        atualizarTotaisBrasil(casos)
        adapter.update(casos)
        adapter.notifyDataSetChanged()
    }

    private fun atualizarTotaisBrasil(casos: List<Casos>) {
        val total = CasosCovidUtil.somarCasos(casos, "")
        updateTextViewCounter(viewTotalConfirmed, total?.confirmed)
        updateTextViewCounter(viewTotalRecovered, total?.recovered)
        updateTextViewCounter(viewTotalDeaths, total?.deaths)
    }

    private fun updateTextViewCounter(viewTotalConfirmed1: TextView, confirmed: Int?) {
        viewTotalConfirmed1.text = confirmed?.toString() ?: "-"
    }


}