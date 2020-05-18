package com.github.fabiosoaza.casos.covid19.util

import android.content.Context
import android.net.ConnectivityManager
import android.widget.TextView
import com.github.fabiosoaza.casos.covid19.R
import com.github.fabiosoaza.casos.covid19.domain.Casos
import com.github.fabiosoaza.casos.covid19.domain.UF
import java.text.MessageFormat

class CasosCovidUtil {
    companion object {

        fun hasConnection(ctx: Context): Boolean {
            val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            return info != null && info.isConnected
        }

        fun somarCasos(casos: List<Casos>?, local:String?=null) : Casos? {
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
                            local = local ,
                            cases = element.cases,
                            confirmed = element.confirmed,
                            deaths = element.deaths,
                            recovered = element.recovered
                        )
                    else
                        Casos(
                            local = local ?: element?.local,
                            cases = intOrNull(element?.cases) + intOrNull(accumulator?.cases),
                            confirmed = intOrNull(element?.confirmed) + intOrNull(accumulator?.confirmed),
                            deaths = intOrNull(element?.deaths) + intOrNull(accumulator?.deaths),
                            recovered = intOrNull(element?.recovered) + intOrNull(accumulator?.recovered)
                        )
                }[0]
            return acumulado
        }

       fun getUFByNomeEstado(context:Context, nome:String): UF {
           val ufsByResourceId = mapOf<Int, UF>(
               R.string.labelAC to UF.ACRE,
               R.string.labelAL to UF.ALAGOAS,
               R.string.labelAP to UF.AMAPA,
               R.string.labelAM to UF.AMAZONAS,
               R.string.labelBA to UF.BAHIA,
               R.string.labelCE to UF.CEARA,
               R.string.labelDF to UF.DISTRITO_FEDERAL,
               R.string.labelES to UF.ESPIRITO_SANTO,
               R.string.labelGO to UF.GOIAS,
               R.string.labelMA to UF.MARANHAO,
               R.string.labelMT to UF.MATO_GROSSO,
               R.string.labelMS to UF.MATO_GROSSO_DO_SUL,
               R.string.labelMG to UF.MINAS_GERAIS,
               R.string.labelPA to UF.PARA,
               R.string.labelPB to UF.PARAIBA,
               R.string.labelPR to UF.PARANA,
               R.string.labelPE to UF.PERNAMBUCO,
               R.string.labelPI to UF.PIAUI,
               R.string.labelRJ to UF.RIO_DE_JANEIRO,
               R.string.labelRN to UF.RIO_GRANDE_DO_NORTE,
               R.string.labelRS to UF.RIO_GRANDE_DO_SUL,
               R.string.labelRO to UF.RONDONIA,
               R.string.labelRR to UF.RORAIMA,
               R.string.labelSC to UF.SANTA_CATARINA,
               R.string.labelSP to UF.SAO_PAULO,
               R.string.labelSE to UF.SERGIPE,
               R.string.labelTO to UF.TOCANTINS
           )
           return ufsByResourceId.filter { entry -> context.getString(entry.key) == nome }
               .map { entry -> entry.value }
               .first()

       }

        fun updateContentDecriptionSummary(
            context: Context?,
            textView: TextView?,
            caso: Casos?,
            templateMessage: String?
        ) {
            val description = MessageFormat.format(
                templateMessage,
                caso?.local,
                CasosCovidUtil.getNoRecordLabelIfNull(context, caso?.confirmed),
                CasosCovidUtil.getNoRecordLabelIfNull(context, caso?.deaths),
                CasosCovidUtil.getNoRecordLabelIfNull(context, caso?.recovered)
            )
            textView?.contentDescription = description
        }


        fun getNoRecordLabelIfNull(context:Context?, value:Int?) : String? {
            return if (value == null) {
                getNoRecordLabelIfNullOrDash(context, "-")
            } else {
                "$value"
            }
        }

        private fun getNoRecordLabelIfNullOrDash(context:Context?, value:String?) : String? {
            return if (value == null || value == "-".trim()) {
                context?.getString(R.string.noRecordComputed)
            } else {
                value
            }

        }

        private fun intOrNull(value : Int?) : Int{
            return value ?: 0
        }
    }
}