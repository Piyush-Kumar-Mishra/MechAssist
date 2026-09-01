package com.example.mechassist.data.remote

import retrofit2.http.GET

interface MechanicApi {
    @GET("mechanics")
    suspend fun getMechanics(): List<MechanicDto>
}
