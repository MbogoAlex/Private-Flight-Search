package com.example.privateflightsearchapp.data

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface AirportsRepository {
    suspend fun insertRoute(favorite: Favorite)
    fun getAirports(): Flow<List<Airport>>
    fun getSpecificAirport(name: String): Flow<List<Airport>>
    fun getFavoriteRoutes(): Flow<List<Favorite>>
    fun getSpecificFavoriteRoute(id: Int): Flow<Favorite>
}

class OfflineAirportsRepository(private val airportDao: AirportDao): AirportsRepository {
    override suspend fun insertRoute(favorite: Favorite) = airportDao.saveRoute(favorite)

    override fun getAirports(): Flow<List<Airport>> = airportDao.getAirports()

    override fun getSpecificAirport(name: String): Flow<List<Airport>> = airportDao.getSpecificAirport("%$name%"
    )

    override fun getFavoriteRoutes(): Flow<List<Favorite>> = airportDao.getFavoriteRoutes()

    override fun getSpecificFavoriteRoute(id: Int): Flow<Favorite> = airportDao.getSpecificFavoriteRoute(id)

}