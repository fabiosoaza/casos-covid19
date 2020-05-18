package com.github.fabiosoaza.casos.covid19.ui

import com.github.fabiosoaza.casos.covid19.domain.CasosCovid19

interface BuscadorCasosComponent {

    fun buscar() : List<CasosCovid19>
    fun atualizarResultados(casos: List<CasosCovid19>)
    fun showProgress(show: Boolean)


}