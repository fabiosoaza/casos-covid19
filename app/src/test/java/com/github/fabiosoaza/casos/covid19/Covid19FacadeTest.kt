package com.github.fabiosoaza.casos.covid19

import com.github.fabiosoaza.casos.covid19.webservice.facade.infrastructure.client.Covid19BrazilApiClient
import com.github.fabiosoaza.casos.covid19.webservice.facade.Covid19Facade
import org.junit.Test
import java.util.*


class Covid19FacadeTest {

    @Test
    fun deveListarOsCasosParaTodosOsEstados() {
        val client = Covid19BrazilApiClient.build()
        val service =
            Covid19Facade(
                client
            )
        service.listarCasosTodosEstados()
    }

    @Test
    fun deveListarOsCasosParaUmUf() {
        val client = Covid19BrazilApiClient.build()
        val service =
            Covid19Facade(
                client
            )
        service.buscarCasosEstado("RS")

    }

    @Test
    fun deveListarOsCasosPorData() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DATE, 1)
        cal.set(Calendar.MONTH, Calendar.MAY)
        cal.set(Calendar.YEAR, 2020)

        val client = Covid19BrazilApiClient.build()
        val service =
            Covid19Facade(
                client
            )
        service.buscarCasosTodosEstadosPorData(cal.time)

    }

    @Test
    fun deveListarosCasosPorPais() {

        val client = Covid19BrazilApiClient.build()
        val service =
            Covid19Facade(
                client
            )
        service.buscarCasosPais("Argentina")

    }

    @Test
    fun deveListarCasosMundo() {

        val client = Covid19BrazilApiClient.build()
        val service =
            Covid19Facade(
                client
            )
        service.listarCasosMundo()

    }


}

