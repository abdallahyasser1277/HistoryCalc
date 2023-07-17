package com.abdalllahyascer.HistoryCalc

import android.app.Application
import android.content.Context

class CalculatorApp:Application() {
    init {
        application=this
    }
    companion object{
        private lateinit var application:Application

        fun getContext(): Context= application.applicationContext
    }
}