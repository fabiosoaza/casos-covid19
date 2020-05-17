package com.github.fabiosoaza.casos.covid19

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal
import java.util.*

class DateTimeUtils {

    companion object {
         fun format(date:Date, pattern:String) : String{
             val formatter = SimpleDateFormat(pattern)
             return formatter.format(date)
         }
    }

}