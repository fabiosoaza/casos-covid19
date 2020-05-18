package com.github.fabiosoaza.casos.covid19.ui

import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.github.fabiosoaza.casos.covid19.R
import com.github.fabiosoaza.casos.covid19.domain.CasosCovid19
import com.github.fabiosoaza.casos.covid19.util.CasosCovidUtil
import com.github.fabiosoaza.casos.covid19.util.UiUtil
import com.github.fabiosoaza.casos.covid19.webservice.facade.Covid19Facade
import jrizani.jrspinner.JRSpinner
import java.net.SocketTimeoutException
import java.util.*

class BuscadorCasosBrasilComponent(
    private var view: ComponentActivity,
    private var facade: Covid19Facade,
    private var covid19AdapterList: ListCasosCovid19Adapter,
    private var barraProgresso: ProgressBarComponent
) : BuscadorCasosComponent {

    private val viewTotalConfirmed: TextView = view.findViewById<TextView>(R.id.txtTotalConfirmed)
    private val viewTotalRecovered: TextView = view.findViewById<TextView>(R.id.txtTotalRecovered)
    private val viewTotalDeaths: TextView = view.findViewById<TextView>(R.id.txtTotalDeaths)
    private val viewLocalSelection: JRSpinner = view.findViewById<JRSpinner>(R.id.JRSpinner)
    private val viewGroupSummaryBrazil = view.findViewById<View>(R.id.viewGroupSummaryBrazil)

    private val labelAllstates = view.getString(R.string.labelAllstates)


    override fun buscar(): List<CasosCovid19> {
        if (!CasosCovidUtil.hasConnection(view)) {
            UiUtil.message(view, view.getString(R.string.noInternetPermissionWarning))
            return mutableListOf<CasosCovid19>()
        }
        val localSelecionado = localSelecionado()
        return try {
            if (localSelecionado == labelAllstates || localSelecionado == "") {
                listarTodosEstados()
            } else {
                buscarEstado(localSelecionado)
            }
        } catch (ex: SocketTimeoutException) {
            UiUtil.message(view, view.getString(R.string.cantConnectToHost))
            mutableListOf<CasosCovid19>()
        }

    }

    private fun localSelecionado() = viewLocalSelection.text.toString()

    private fun buscarEstado(localSelecionado: String): MutableList<CasosCovid19> {
        val list = mutableListOf<CasosCovid19>()
        val ufByNomeEstado = CasosCovidUtil.getUFByNomeEstado(view, localSelecionado)
        val uf = ufByNomeEstado.sigla.toLowerCase(Locale.getDefault())
        val element = facade.buscarCasosEstado(uf)
        if (element != null) {
            list.add(element)
        }
        return list
    }

    private fun listarTodosEstados(): List<CasosCovid19> {
        return facade.listarCasosTodosEstados()
    }


    override fun showProgress(show: Boolean) {
        if (show) {
            barraProgresso.show()
        } else {
            barraProgresso.hide()
        }

    }

    override fun atualizarResultados(casos: List<CasosCovid19>) {
        atualizarTotaisBrasil(casos)
        covid19AdapterList.update(casos)
        covid19AdapterList.notifyDataSetChanged()
    }

    private fun atualizarTotaisBrasil(casos: List<CasosCovid19>) {
        val local = if (casos.size > 1) {
            view.getString(R.string.labelBrazil)
        } else {
            localSelecionado()
        }
        val total = CasosCovidUtil.somarCasos(casos, local)
        updateTextViewCounter(viewTotalConfirmed, total?.confirmed)
        updateTextViewCounter(viewTotalRecovered, total?.recovered)
        updateTextViewCounter(viewTotalDeaths, total?.deaths)
        CasosCovidUtil.updateContentDecriptionSummary(
            view.baseContext,
            viewGroupSummaryBrazil,
            total,
            view.baseContext.getString(R.string.contentDescriptionTotalCasesSummary)
        )

    }

    private fun updateTextViewCounter(textView: TextView, value: Int?) {
        textView.text = value?.toString() ?: "-"
    }


}