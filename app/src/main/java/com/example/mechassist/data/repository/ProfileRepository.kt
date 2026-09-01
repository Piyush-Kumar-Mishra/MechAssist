package com.example.mechassist.data.repository

import com.example.mechassist.data.local.ProfileDao
import com.example.mechassist.data.local.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {
    fun getProfile(): Flow<ProfileEntity?> {
        return profileDao.getProfile()
    }

    suspend fun getProfileOnce(): ProfileEntity? {
        return profileDao.getProfileOnce()
    }

    suspend fun saveProfile(profile: ProfileEntity) {
        profileDao.saveProfile(profile)
    }
}
