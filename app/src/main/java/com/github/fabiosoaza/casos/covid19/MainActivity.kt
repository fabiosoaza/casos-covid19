package com.github.fabiosoaza.casos.covid19

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.fabiosoaza.casos.covid19.domain.Casos
import com.github.fabiosoaza.casos.covid19.domain.UF
import com.github.fabiosoaza.casos.covid19.ui.*
import com.github.fabiosoaza.casos.covid19.util.ResourceUtil
import com.github.fabiosoaza.casos.covid19.webservice.facade.Covid19Facade
import com.github.fabiosoaza.casos.covid19.webservice.facade.infrastructure.client.Covid19BrazilaApiClient
import jrizani.jrspinner.JRSpinner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var asyncTaskBrasil: BuscadorCasosAyncTask? = null
    private var asyncTaskMundo: BuscadorCasosAyncTask? = null

    private var casos = mutableListOf<Casos>()
    private var casosAdapter = CasosAdapter(casos)

    private var covid19Facade = Covid19Facade(Covid19BrazilaApiClient.build())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSpinner()
        carregarDadosBrasil()
        carregarDadosMundo()
        initRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_refresh -> {
            carregarDadosBrasil()
            carregarDadosMundo()
            true
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun carregarDadosBrasil() {
        if (asyncTaskBrasil?.status != AsyncTask.Status.RUNNING) {
            val buscadorCasosComponent = BuscadorCasosBrasilComponent(this, covid19Facade, casosAdapter, ProgressBarComponent(this))
            asyncTaskBrasil = BuscadorCasosAyncTask(buscadorCasosComponent)
            asyncTaskBrasil?.execute()
        }
    }

    fun carregarDadosMundo() {
        if (asyncTaskMundo?.status != AsyncTask.Status.RUNNING) {
            val buscadorCasosComponent = BuscadorCasosMundoComponent(this, covid19Facade, ProgressBarComponent(this))
            asyncTaskMundo = BuscadorCasosAyncTask(buscadorCasosComponent)
            asyncTaskMundo?.execute()
        }
    }



    private fun initRecyclerView(){
        val layoutManager =LinearLayoutManager(this)
        rvResultadosCovid19.adapter = casosAdapter
        rvResultadosCovid19.layoutManager = layoutManager

    }

    private fun setupSpinner() {
        val mJRSpinner: JRSpinner = findViewById<JRSpinner>(R.id.JRSpinner)
        val all = mutableListOf<String>()
        all.add(getString(R.string.labelAllstates))

        val estados : List<String> = UF.siglas().map { uf ->
            val resourceValue = ResourceUtil.getStringResourceByName(this, "label$uf")
            resourceValue

        }.filterNotNull()

        all.addAll(estados)




        val locais = all.toTypedArray<String>()

        mJRSpinner.setItems(locais)

        mJRSpinner.setOnItemClickListener {

            carregarDadosBrasil()
        }

        mJRSpinner.select(0)
    }


}
