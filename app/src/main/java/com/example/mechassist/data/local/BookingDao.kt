package com.example.mechassist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY bookedAt DESC")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Insert
    suspend fun insertBooking(booking: BookingEntity)
}
