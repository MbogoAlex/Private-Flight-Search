package com.example.privateflightsearchapp

import android.app.Application
import com.example.privateflightsearchapp.data.AppContainer
import com.example.privateflightsearchapp.data.DefaultContainer

class FlightSearchApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)
    }
}