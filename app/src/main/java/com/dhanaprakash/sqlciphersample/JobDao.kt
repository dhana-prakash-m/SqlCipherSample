package com.dhanaprakash.sqlciphersample

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JobsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<Job>)

    @Query("SELECT * FROM Job")
    fun observeJobs(): Flow<List<Job>>
}