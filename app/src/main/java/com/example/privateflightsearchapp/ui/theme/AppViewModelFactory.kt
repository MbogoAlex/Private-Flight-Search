package com.example.privateflightsearchapp.ui.theme

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.privateflightsearchapp.FlightSearchApplication
import com.example.privateflightsearchapp.ui.screens.AllRoutesViewModel

class AppViewModelFactory {
    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                val airportsRepository = application.container.airportsRepository
                AllRoutesViewModel(
                    airportsRepository = airportsRepository
                )
            }
        }
    }
}