package com.example.privateflightsearchapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveRoute(favorite: Favorite)

    @Query("SELECT * FROM airport")
    fun getAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE name LIKE :name")
    fun getSpecificAirport(name: String): Flow<List<Airport>>

    @Query("SELECT * FROM favorite")
    fun getFavoriteRoutes(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getSpecificFavoriteRoute(id: Int): Flow<Favorite>
}