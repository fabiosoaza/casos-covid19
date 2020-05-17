package com.github.fabiosoaza.casos.covid19.util

import android.content.Context
import android.net.ConnectivityManager
import com.github.fabiosoaza.casos.covid19.domain.Casos

class CasosCovidUtil {
    companion object {

        fun hasConnection(ctx: Context): Boolean {
            val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            return info != null && info.isConnected
        }

        fun somarCasos(casos: List<Casos>?, local:String) : Casos? {
            if(casos==null || casos.isEmpty()){
                return Casos(
                    local = local,
                    cases =  0,
                    confirmed = 0,
                    deaths = 0,
                    recovered = 0
                )
            }
            val acumulado = casos.groupingBy { 0 }
                ?.aggregate { _, accumulator: Casos?, element, first ->
                    if (first)
                        Casos(
                            local = local,
                            cases = element.cases,
                            confirmed = element.confirmed,
                            deaths = element.deaths,
                            recovered = element.recovered
                        )
                    else
                        Casos(
                            local = local,
                            cases = intOrNull(element?.cases) + intOrNull(accumulator?.cases),
                            confirmed = intOrNull(element?.confirmed) + intOrNull(accumulator?.confirmed),
                            deaths = intOrNull(element?.deaths) + intOrNull(accumulator?.deaths),
                            recovered = intOrNull(element?.recovered) + intOrNull(accumulator?.recovered)
                        )
                }[0]
            return acumulado
        }

        private fun intOrNull(value : Int?) : Int{
            return value ?: 0
        }
    }
}