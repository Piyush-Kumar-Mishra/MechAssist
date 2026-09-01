package com.example.mechassist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mechassist.data.local.BookingEntity
import com.example.mechassist.data.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    val bookings: Flow<List<BookingEntity>> = bookingRepository.getAllBookings()
}
