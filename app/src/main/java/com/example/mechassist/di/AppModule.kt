package com.example.mechassist.di

import android.content.Context
import androidx.room.Room
import com.example.mechassist.data.local.AppDatabase
import com.example.mechassist.data.local.BookingDao
import com.example.mechassist.data.local.ProfileDao
import com.example.mechassist.data.remote.MechanicApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://6a96ef6d0e3240db90618e97.mockapi.io/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMechanicApi(retrofit: Retrofit): MechanicApi {
        return retrofit.create(MechanicApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mechassist_db"
        ).fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideProfileDao(db: AppDatabase): ProfileDao {
        return db.profileDao()
    }

    @Provides
    fun provideBookingDao(db: AppDatabase): BookingDao {
        return db.bookingDao()
    }
}
