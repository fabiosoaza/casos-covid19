package com.github.fabiosoaza.casos.covid19.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.fabiosoaza.casos.covid19.R
import com.github.fabiosoaza.casos.covid19.domain.CasosCovid19
import com.github.fabiosoaza.casos.covid19.util.CasosCovidUtil
import kotlinx.android.synthetic.main.caso_local_item.view.*

class ListCasosCovid19Adapter(private val casos: MutableList<CasosCovid19>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0
    private val TYPE_ITEM: Int = 1

    fun update(novosCasos: List<CasosCovid19>){
        casos.clear()
        casos.addAll(novosCasos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = layoutInflater(parent, viewType)
        return if (viewType == TYPE_HEADER) {
            CasosHeaderViewHolder(itemView)
        } else {
            CasosItemViewHolder(itemView)
        }
    }

    private fun layoutInflater(parent: ViewGroup, viewType: Int): View {
        return if (viewType == TYPE_HEADER) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.caso_local_item_header, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.caso_local_item, parent, false)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return casos.size + 1
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is CasosItemViewHolder) {
            val holder : CasosItemViewHolder? = viewHolder
            val caso = casos[position - 1]
            updateItemHolder(holder, caso)
            CasosCovidUtil.updateContentDecriptionSummary(
                holder?.itemView?.context,
                holder?.viewGroupCaseByStateItem,
                caso,
                holder?.itemView?.context?.getString(R.string.contentDescriptionItemList)
            )
        }
    }

    private fun updateItemHolder(holder: CasosItemViewHolder?, caso: CasosCovid19  ) {
        updateTextViewCounter( holder?.txtLocal, caso.local)
        updateTextViewCounter( holder?.txtConfirmados, caso.confirmed)
        updateTextViewCounter( holder?.txtRecuperados, caso.recovered)
        updateTextViewCounter( holder?.txtMortes, caso.deaths)
    }

    private fun updateTextViewCounter(viewTotal: TextView?, value: Int? ) {
        viewTotal?.text = value?.toString() ?: "-"
    }

    private fun updateTextViewCounter(viewTotal: TextView?, value: String? ) {
        viewTotal?.text = value ?: "-"
    }



    class CasosItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtLocal: TextView = itemView.txtLocal
        var txtConfirmados: TextView = itemView.txtConfirmados
        var txtRecuperados: TextView = itemView.txtRecuperados
        var txtMortes: TextView = itemView.txtMortos
        var viewGroupCaseByStateItem: View = itemView.viewGroupCaseByStateItem


    }

    class CasosHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}



