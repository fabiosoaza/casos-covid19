package com.github.fabiosoaza.casos.covid19.ui

import android.os.AsyncTask
import com.github.fabiosoaza.casos.covid19.domain.CasosCovid19

class BuscadorCasosAyncTask(var buscadorBrasil: BuscadorCasosComponent) : AsyncTask<Void, Void, List<CasosCovid19>>() {

    override fun onPreExecute() {
        super.onPreExecute()
        buscadorBrasil.showProgress(true)

    }

    override fun onPostExecute(result: List<CasosCovid19>) {
        super.onPostExecute(result)
        buscadorBrasil.atualizarResultados(result)
        buscadorBrasil.showProgress(false)

    }

    override fun doInBackground(vararg params: Void?): List<CasosCovid19> {
        return buscadorBrasil.buscar()
    }

}