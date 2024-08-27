package com.example.shanks

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shanks.data.db.JobDao
import com.example.shanks.data.model.JobEntity


@Database(entities = [JobEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
}
