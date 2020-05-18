package com.github.fabiosoaza.casos.covid19.webservice.facade

import com.github.fabiosoaza.casos.covid19.DateTimeUtils
import com.github.fabiosoaza.casos.covid19.domain.CasosCovid19
import com.github.fabiosoaza.casos.covid19.webservice.facade.infrastructure.client.Covid19BrazilaApiClient
import com.github.fabiosoaza.casos.covid19.webservice.facade.infrastructure.client.CovidApiConverter
import java.util.*

 class Covid19Facade (client: Covid19BrazilaApiClient) {

    private val client = client

   fun listarCasosTodosEstados(): List<CasosCovid19>{

         val result = client.listarCasosTodosEstados().execute()
         val body = result.body()

         return CovidApiConverter()
             .casosTodosUf(result.body()).sortedBy { item -> item.local }
     }

    fun listarCasosMundo(): List<CasosCovid19> {
        val result = client.buscarCasosMundo().execute()
        return CovidApiConverter()
            .casosMundo(result.body())
    }

     fun buscarCasosEstado(uf: String): CasosCovid19? {
         val result = client.buscarCasosEstado(uf).execute()
         val body = result.body()
         return CovidApiConverter()
             .casosEstado(result.body())

     }

     fun buscarCasosTodosEstadosPorData(data:Date): List<CasosCovid19> {

         val data = DateTimeUtils.format(data, "yyyyMMdd")
         val result = client.listarCasosTodosEstadosData(data).execute()
         val body = result.body()
         return CovidApiConverter()
             .casosTodosUf(result.body()).sortedBy { item -> item.local }
     }

     fun buscarCasosPais(pais: String): List<CasosCovid19> {
         val result = client.buscarCasosPais(pais).execute()
         return CovidApiConverter()
             .casosPais(result.body())
     }


}