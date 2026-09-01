package com.example.mechassist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mechassist.data.local.ProfileEntity
import com.example.mechassist.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _customerName = MutableStateFlow("")
    val customerName: StateFlow<String> = _customerName

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _vehicleNumber = MutableStateFlow("")
    val vehicleNumber: StateFlow<String> = _vehicleNumber

    private val _vehicleModel = MutableStateFlow("")
    val vehicleModel: StateFlow<String> = _vehicleModel

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city

    private val _profileExists = MutableStateFlow<Boolean?>(null)
    val profileExists: StateFlow<Boolean?> = _profileExists

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            val profile = profileRepository.getProfileOnce()
            if (profile != null) {
                _customerName.value = profile.customerName
                _phone.value = profile.phone
                _vehicleNumber.value = profile.vehicleNumber
                _vehicleModel.value = profile.vehicleModel
                _city.value = profile.city
                _profileExists.value = true
            } else {
                _profileExists.value = false
            }
        }
    }

    fun onCustomerNameChange(value: String) { _customerName.value = value }
    fun onPhoneChange(value: String) { _phone.value = value }
    fun onVehicleNumberChange(value: String) { _vehicleNumber.value = value }
    fun onVehicleModelChange(value: String) { _vehicleModel.value = value }
    fun onCityChange(value: String) { _city.value = value }

    fun saveProfile() {
        // Validate
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
        if (_vehicleModel.value.isBlank()) {
            _errorMessage.value = "Please enter your vehicle model (e.g. Honda City)"
            return
        }
        if (_city.value.isBlank()) {
            _errorMessage.value = "Please enter your city / location"
            return
        }

        viewModelScope.launch {
            val profile = ProfileEntity(
                customerName = _customerName.value.trim(),
                phone = _phone.value.trim(),
                vehicleNumber = _vehicleNumber.value.trim(),
                vehicleModel = _vehicleModel.value.trim(),
                city = _city.value.trim()
            )
            profileRepository.saveProfile(profile)
            _saveSuccess.value = true
            _profileExists.value = true
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }
}
