package com.example.shanks.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shanks.data.model.JobEntity

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobEntity)

    @Delete
    suspend fun deleteJob(job: JobEntity)

    @Query("SELECT * FROM bookmarks WHERE id = :id")
    suspend fun getJobById(id: Int): JobEntity?

    @Query("SELECT * FROM bookmarks")
    suspend fun getAllJobs(): List<JobEntity>

    @Query("SELECT COUNT(*) > 0 FROM bookmarks WHERE id = :id")
    fun isBookmarked(id: Int): LiveData<Boolean>
}