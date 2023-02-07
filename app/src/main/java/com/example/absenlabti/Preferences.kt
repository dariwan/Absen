package com.example.absenlabti

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val STATUS = "status"
    private val LEVEL = "level"
    private val APP = "app"

    private val pref : SharedPreferences = context.getSharedPreferences(APP, Context.MODE_PRIVATE)

    //getter dan setter
    var prefStatus: Boolean
        get() = pref.getBoolean(STATUS, false)
        set(value) = pref.edit().putBoolean(STATUS, value).apply()

    var prefLevel: String?
        get() = pref.getString(LEVEL, "")
        set(value) = pref.edit().putString(LEVEL, value).apply()

    fun prefClear(){
        pref.edit().remove(STATUS).apply()
        pref.edit().remove(LEVEL).apply()
    }
}