package com.github.fabiosoaza.casos.covid19.webservice.facade.infrastructure.client

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.fabiosoaza.casos.covid19.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


interface Covid19BrazilaApiClient {


    @GET("report/v1")
    fun listarCasosTodosEstados(): Call<CasosTodosUF>

    @GET("report/v1/brazil/uf/{uf}")
    fun buscarCasosEstado(@Path("uf") uf : String): Call<CasosUF>

    @GET("report/v1/brazil/{data}")
    fun listarCasosTodosEstadosData(@Path("data") data:String): Call<CasosTodosUF>

    @GET("report/v1/{pais}")
    fun buscarCasosPais(@Path("pais") pais : String): Call<CasosPais>

    @GET("report/v1/countries")
    fun buscarCasosMundo(): Call<Casos>


    companion object Builder {

        fun build(): Covid19BrazilaApiClient {

            val objectMapper  = ObjectMapper()
            objectMapper.registerModule(JavaTimeModule())
            objectMapper.registerModule( KotlinModule())
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(6, TimeUnit.SECONDS)
                .connectTimeout(6, TimeUnit.SECONDS)
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.COVID_BRAZIL_API_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .build()

            return retrofit.create(Covid19BrazilaApiClient::class.java)

        }
    }

}