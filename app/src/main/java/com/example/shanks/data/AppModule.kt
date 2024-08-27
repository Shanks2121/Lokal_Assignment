package com.example.shanks.data

import android.content.Context
import androidx.room.Room
import com.example.shanks.data.api.JobApiService
import com.example.shanks.AppDatabase
import com.example.shanks.data.db.JobDao
import com.example.shanks.data.repository.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://testapi.getlokalapp.com/"
private const val DATABASE = "job_database"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideJobApiService(retrofit: Retrofit): JobApiService {
        return retrofit.create(JobApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideJobRepository(apiService: JobApiService, jobDao: JobDao): JobRepository {
        return JobRepository(apiService, jobDao)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE
        ).build()
    }

    @Provides
    fun provideJobDao(database: AppDatabase): JobDao {
        return database.jobDao()
    }
}