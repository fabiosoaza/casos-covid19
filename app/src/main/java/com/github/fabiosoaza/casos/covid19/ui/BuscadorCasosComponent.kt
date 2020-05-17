package com.github.fabiosoaza.casos.covid19.ui

import com.github.fabiosoaza.casos.covid19.domain.Casos

interface BuscadorCasosComponent {

    fun buscar() : List<Casos>
    fun atualizarResultados(casos: List<Casos>)
    fun showProgress(show: Boolean)


}