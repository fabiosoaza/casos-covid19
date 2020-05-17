package com.github.fabiosoaza.casos.covid19.ui

import android.os.AsyncTask
import com.github.fabiosoaza.casos.covid19.domain.Casos

class BuscadorCasosAyncTask(var buscadorBrasil: BuscadorCasosComponent) : AsyncTask<Void, Void, List<Casos>>() {

    override fun onPreExecute() {
        super.onPreExecute()
        buscadorBrasil.showProgress(true)

    }

    override fun onPostExecute(result: List<Casos>) {
        super.onPostExecute(result)
        buscadorBrasil.atualizarResultados(result)
        buscadorBrasil.showProgress(false)

    }

    override fun doInBackground(vararg params: Void?): List<Casos> {
        return buscadorBrasil.buscar()
    }

}