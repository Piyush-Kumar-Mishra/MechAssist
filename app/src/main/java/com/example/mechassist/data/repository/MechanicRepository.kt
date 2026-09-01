package com.example.mechassist.data.repository

import com.example.mechassist.data.remote.MechanicApi
import com.example.mechassist.data.remote.MechanicDto
import javax.inject.Inject

class MechanicRepository @Inject constructor(
    private val api: MechanicApi
) {
    suspend fun getMechanics(): Result<List<MechanicDto>> {
        return try {
            val mechanics = api.getMechanics()
            Result.success(mechanics)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
