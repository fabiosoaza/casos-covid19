package com.github.fabiosoaza.casos.covid19.webservice.facade.infrastructure.client.dto

import java.util.*

data class CasosUF(var uid:Int?=null, var state:String?=null, var uf:String?=null, var cases:Int?=null, var deaths:Int?=null, var suspects:Int?=null, var refuses:Int?=null, var broadcast:Boolean?=null, var comments:String?=null, var datetime:Date?=null  ) {
}

