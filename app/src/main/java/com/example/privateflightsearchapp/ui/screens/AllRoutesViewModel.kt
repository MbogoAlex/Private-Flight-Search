package com.example.privateflightsearchapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.privateflightsearchapp.data.Airport
import com.example.privateflightsearchapp.data.AirportsRepository
import com.example.privateflightsearchapp.data.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FlightPath(
    val airports: List<Airport> = emptyList(),
    val searchSuggestions: List<String> = emptyList(),
    val paths: List<Path> = emptyList(),
    val isSearching: Boolean = false,
    val searched: Boolean = false
)

data class Path(
    val from: Airport,
    val to: Airport,
)


class AllRoutesViewModel(private val airportsRepository: AirportsRepository): ViewModel() {
    private val _uiState = MutableStateFlow(value = FlightPath())
    val uiState: StateFlow<FlightPath> = _uiState.asStateFlow()

    var searchText by mutableStateOf("")

    var airportsList: List<Airport> = mutableListOf()

    fun getAllAirports() {
        viewModelScope.launch {
            airportsRepository.getAirports().onEach {
                _uiState.value = FlightPath(airports = it)
                airportsList = it
            }
                .launchIn(this)
        }
    }

    init {
        getAllAirports()
    }

    fun getSuggestions() {
        viewModelScope.launch {
            airportsRepository.getSpecificAirport(searchText)
                .onEach { airports ->
                    val suggestions = airports.map { it.name }
                    _uiState.update {
                        it.copy(
                            searchSuggestions = suggestions,
                            isSearching = searchText.isNotEmpty()
                        )
                    }
                }
                .launchIn(this)
        }
    }


    fun getFlightPath() {
        viewModelScope.launch {
            airportsRepository.getSpecificAirport(searchText)
                .onEach { airports ->
                    val shuffledAirports = airports.shuffled()
                    val paths: MutableList<Path> = mutableListOf()
                    for(airport in airportsList) {
                        for(fromAirport in shuffledAirports) {
                            val path = Path(fromAirport, airport)
                            paths.add(path)
                        }
                    }
                    _uiState.update {
                        it.copy(
                            paths = paths,
                            searched = true
                        )
                    }
                }
                .launchIn(this)
        }
    }



    fun cancelSearch() {
        _uiState.update {
            it.copy(
                isSearching = false
            )
        }
    }

}
