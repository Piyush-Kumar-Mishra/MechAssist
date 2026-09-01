package com.example.mechassist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mechassist.data.remote.MechanicDto
import com.example.mechassist.data.repository.MechanicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MechanicViewModel @Inject constructor(
    private val repository: MechanicRepository
) : ViewModel() {

    private val _mechanics = MutableStateFlow<List<MechanicDto>>(emptyList())

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedFilter = MutableStateFlow<String?>(null)
    val selectedFilter: StateFlow<String?> = _selectedFilter

    private val _filteredMechanics = MutableStateFlow<List<MechanicDto>>(emptyList())
    val filteredMechanics: StateFlow<List<MechanicDto>> = _filteredMechanics

    init {
        loadMechanics()
    }

    fun loadMechanics() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getMechanics()
            result.onSuccess { list ->
                _mechanics.value = list
                applyFilters()
                _isLoading.value = false
            }.onFailure { e ->
                _error.value = e.message ?: "Failed to load mechanics. Please check your connection."
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    fun onFilterSelected(filter: String?) {
        _selectedFilter.value = if (_selectedFilter.value == filter) null else filter
        applyFilters()
    }

    private fun applyFilters() {
        var list = _mechanics.value

        // Search across name, location, address, and services
        val query = _searchQuery.value.trim()
        if (query.isNotEmpty()) {
            list = list.filter { mechanic ->
                mechanic.name.contains(query, ignoreCase = true) ||
                mechanic.location.contains(query, ignoreCase = true) ||
                mechanic.address.contains(query, ignoreCase = true) ||
                mechanic.services.contains(query, ignoreCase = true)
            }
        }

        // Apply chip filter
        when (_selectedFilter.value) {
            "Open" -> list = list.filter { it.open }
            "Closed" -> list = list.filter { !it.open }
            "4+ Rating" -> list = list.filter { it.rating >= 4.0 }
            "3+ Rating" -> list = list.filter { it.rating >= 3.0 }
            "2+ Rating" -> list = list.filter { it.rating >= 2.0 }
        }

        _filteredMechanics.value = list
    }

    fun getMechanicByName(name: String): MechanicDto? {
        return _mechanics.value.find { it.name.equals(name, ignoreCase = true) }
    }
}
