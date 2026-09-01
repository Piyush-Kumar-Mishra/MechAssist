package com.example.mechassist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val customerName: String,
    val phone: String,
    val vehicleNumber: String,
    val vehicleModel: String,
    val city: String
)
