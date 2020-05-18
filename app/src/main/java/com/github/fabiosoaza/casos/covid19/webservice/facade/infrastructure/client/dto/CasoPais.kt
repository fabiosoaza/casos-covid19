package com.github.fabiosoaza.casos.covid19.webservice.facade.infrastructure.client.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class CasoPais(var country:String?= null, var cases:Int?=null, var confirmed:Int?=null, var deaths:Int?=null, var recovered:Int?=null, @JsonProperty("updated_at") var updatedAt: Date?=null) {
}