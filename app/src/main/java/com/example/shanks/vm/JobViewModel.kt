package com.example.shanks.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shanks.data.model.Job
import com.example.shanks.data.model.JobEntity
import com.example.shanks.data.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val repository: JobRepository
) : ViewModel() {

    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs: StateFlow<List<Job>> get() = _jobs

    private val _bookmarkedJobs = MutableLiveData<List<JobEntity>>()
    val bookmarkedJobs: LiveData<List<JobEntity>> get() = _bookmarkedJobs

    val isLoading = MutableLiveData(true)

    init {
        fetchJobs()
        getBookmarks()
    }

    fun fetchJobs() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                for (page in 1..3) {
                    val response = repository.getJobs(page = page)
                    _jobs.value += response.results
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                Log.d("JobViewModel", "$e")
            }
        }
    }


    fun getBookmarks() {
        viewModelScope.launch {
            _bookmarkedJobs.value = repository.getBookmarkedAllJobs()
            Log.d("LaunchViewModel", "Favorites = ${_bookmarkedJobs.value}")

        }
    }

    fun insertBookmark(job: JobEntity) {
        viewModelScope.launch {
            repository.insertBookmark(job)
            Log.d("LaunchViewModel", "Added $job")
        }
    }

    fun deleteBookmark(job: JobEntity) {
        viewModelScope.launch {
            repository.deleteBookmark(job = job)
            Log.d("LaunchViewModel", "Removed $job")
        }
    }

    fun isBookmarked(id: Int): LiveData<Boolean> {
        return repository.isBookmarked(id = id)
    }
}
