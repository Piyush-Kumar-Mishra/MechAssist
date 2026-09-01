package com.example.mechassist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mechassist.data.local.BookingEntity
import com.example.mechassist.data.local.ProfileEntity
import com.example.mechassist.data.repository.BookingRepository
import com.example.mechassist.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _customerName = MutableStateFlow("")
    val customerName: StateFlow<String> = _customerName

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _vehicleNumber = MutableStateFlow("")
    val vehicleNumber: StateFlow<String> = _vehicleNumber

    private val _selectedService = MutableStateFlow("")
    val selectedService: StateFlow<String> = _selectedService

    private val _problemDescription = MutableStateFlow("")
    val problemDescription: StateFlow<String> = _problemDescription

    private val _bookingSuccess = MutableStateFlow(false)
    val bookingSuccess: StateFlow<Boolean> = _bookingSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadProfileDefaults() {
        viewModelScope.launch {
            val profile = profileRepository.getProfileOnce()
            if (profile != null) {
                _customerName.value = profile.customerName
                _phone.value = profile.phone
                _vehicleNumber.value = profile.vehicleNumber
            }
        }
    }

    fun onCustomerNameChange(value: String) { _customerName.value = value }
    fun onPhoneChange(value: String) { _phone.value = value }
    fun onVehicleNumberChange(value: String) { _vehicleNumber.value = value }
    fun onServiceChange(value: String) { _selectedService.value = value }
    fun onProblemDescriptionChange(value: String) { _problemDescription.value = value }

    fun submitBooking(mechanicName: String, mechanicLocation: String) {
        // Validate inputs
        if (_customerName.value.isBlank()) {
            _errorMessage.value = "Please enter your name"
            return
        }
        if (_phone.value.length != 10) {
            _errorMessage.value = "Please enter a valid 10-digit phone number"
            return
        }
        if (_vehicleNumber.value.isBlank()) {
            _errorMessage.value = "Please enter your vehicle number"
            return
        }
        if (_selectedService.value.isBlank()) {
            _errorMessage.value = "Please select a service"
            return
        }

        viewModelScope.launch {
            val booking = BookingEntity(
                mechanicName = mechanicName,
                mechanicLocation = mechanicLocation,
                customerName = _customerName.value.trim(),
                phone = _phone.value.trim(),
                vehicleNumber = _vehicleNumber.value.trim(),
                service = _selectedService.value,
                problemDescription = _problemDescription.value.trim()
            )
            bookingRepository.insertBooking(booking)
            _bookingSuccess.value = true
        }
    }

    fun resetBookingState() {
        _bookingSuccess.value = false
        _errorMessage.value = null
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
