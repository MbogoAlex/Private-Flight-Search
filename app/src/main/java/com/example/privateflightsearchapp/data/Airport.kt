package com.example.privateflightsearchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "iata_code")
    val iataCode: String,
    val passengers: Int
)
