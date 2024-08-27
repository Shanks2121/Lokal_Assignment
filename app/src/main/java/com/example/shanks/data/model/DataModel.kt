package com.example.shanks.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Job(
    val id: Int,
    val title: String,
    val primary_details: PrimaryDetails,
    val company_name: String,
    val created_on: String,
    val whatsapp_no: String,
    val custom_link: String
)

data class PrimaryDetails(
    val Place: String,
    val Salary: String,
    val Job_Type: String,
    val Experience: String,
    val Qualification: String
)


data class JobResponse(
    val results: List<Job>
)

@Entity(tableName = "bookmarks")
data class JobEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val companyName: String,
    val place: String,
    val salary: String,
    val phoneNumber: String?
)