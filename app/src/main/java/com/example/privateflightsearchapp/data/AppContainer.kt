package com.example.privateflightsearchapp.data

import android.content.Context

interface AppContainer {
    val airportsRepository: AirportsRepository
}

class DefaultContainer(private val context: Context): AppContainer {
    override val airportsRepository: AirportsRepository by lazy {
        OfflineAirportsRepository(AppDatabase.getDatabase(context).airportDao())
    }
}