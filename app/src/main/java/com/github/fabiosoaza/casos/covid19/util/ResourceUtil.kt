package com.github.fabiosoaza.casos.covid19.util

import android.content.Context
import android.content.res.Resources


class ResourceUtil {
    companion object {

        fun getStringResourceByName(context: Context, aString: String, packageName:String?=context?.packageName): String? {
            return try {
                val resId: Int = context.resources.getIdentifier(aString, "string", packageName)
                context.getString(resId)
            } catch(ex: Resources.NotFoundException){
                null
            }
        }
    }
}