package com.example.shanks.data.repository

import androidx.lifecycle.LiveData
import com.example.shanks.data.api.JobApiService
import com.example.shanks.data.db.JobDao
import com.example.shanks.data.model.JobEntity
import com.example.shanks.data.model.JobResponse
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val apiService: JobApiService, private val jobDao: JobDao
) {

    suspend fun getJobs(page: Int): JobResponse {
        return apiService.getJobs(page)
    }

    suspend fun getBookmarkedAllJobs() = jobDao.getAllJobs()

    suspend fun insertBookmark(job: JobEntity) = jobDao.insertJob(job)

    suspend fun deleteBookmark(job: JobEntity) {
        val bookmarkedJob = jobDao.getJobById(job.id)
        if (bookmarkedJob != null) {
            jobDao.deleteJob(job)
        }
    }

    fun isBookmarked(id: Int): LiveData<Boolean> {
        return jobDao.isBookmarked(id)
    }
}
