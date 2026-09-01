package com.example.mechassist.data.remote

import com.google.gson.annotations.SerializedName

data class MechanicDto(
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("distance") val distance: Double,
    @SerializedName("location") val location: String,
    @SerializedName("address") val address: String,
    @SerializedName("services") val services: String,
    @SerializedName("open") val open: Boolean,
    @SerializedName("workingHours") val workingHours: String,
    @SerializedName("phone") val phone: String
)
