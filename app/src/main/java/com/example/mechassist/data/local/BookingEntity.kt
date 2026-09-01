package com.example.mechassist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mechanicName: String,
    val mechanicLocation: String,
    val customerName: String,
    val phone: String,
    val vehicleNumber: String,
    val service: String,
    val problemDescription: String,
    val bookedAt: Long = System.currentTimeMillis()
)
