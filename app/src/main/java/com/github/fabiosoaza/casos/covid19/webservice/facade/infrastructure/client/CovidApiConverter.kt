package com.github.fabiosoaza.casos.covid19.webservice.facade.infrastructure.client

class CovidApiConverter {

    fun casosTodosUf(casosTodosUF: CasosTodosUF?): List<com.github.fabiosoaza.casos.covid19.domain.Casos> {
        val casos = mutableListOf<com.github.fabiosoaza.casos.covid19.domain.Casos>()
        if (casosTodosUF?.data != null) {
            casosTodosUF?.data?.iterator()?.forEach { element ->
                val caso = com.github.fabiosoaza.casos.covid19.domain.Casos(
                    local = element.state,
                    cases = element.cases,
                    confirmed = element.cases,
                    deaths = element.deaths
                )
                casos.add(caso)
            }
        }
        return casos
    }


    fun casosPais(casosPais: CasosPais?): List<com.github.fabiosoaza.casos.covid19.domain.Casos> {
        val casos = mutableListOf<com.github.fabiosoaza.casos.covid19.domain.Casos>()
        if (casosPais?.data?.country != null) {
            val caso = com.github.fabiosoaza.casos.covid19.domain.Casos(
                local = casosPais.data!!.country,
                cases = casosPais.data!!.cases,
                confirmed = casosPais.data!!.confirmed,
                deaths = casosPais.data!!.deaths,
                recovered = casosPais.data!!.recovered
            )
            casos.add(caso)
        }
        return casos;
    }

    fun casosMundo(casosMundo: Casos?): List<com.github.fabiosoaza.casos.covid19.domain.Casos> {
        val casos = mutableListOf<com.github.fabiosoaza.casos.covid19.domain.Casos>()
        if (casosMundo?.data != null) {
            casosMundo?.data?.iterator()?.forEach { element ->
                val caso = com.github.fabiosoaza.casos.covid19.domain.Casos(
                    local = element.country,
                    cases = element.cases,
                    confirmed = element.confirmed,
                    deaths = element.deaths,
                    recovered = element.recovered
                )
                casos.add(caso)
            }
        }
        return casos
    }

    fun casosEstado(casosUF: CasosUF?) : com.github.fabiosoaza.casos.covid19.domain.Casos? {
        if (casosUF?.state != null) {
            return com.github.fabiosoaza.casos.covid19.domain.Casos(
                local = casosUF.state,
                cases = casosUF.cases,
                confirmed = casosUF.cases,
                deaths = casosUF.deaths
            )
        }
        return null;
    }

}