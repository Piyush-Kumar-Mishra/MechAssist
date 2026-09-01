package com.example.mechassist.data.repository

import com.example.mechassist.data.local.BookingDao
import com.example.mechassist.data.local.BookingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val bookingDao: BookingDao
) {
    fun getAllBookings(): Flow<List<BookingEntity>> {
        return bookingDao.getAllBookings()
    }

    suspend fun insertBooking(booking: BookingEntity) {
        bookingDao.insertBooking(booking)
    }
}
