package com.poc.pdld.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.poc.pdld.data.Results


@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addResult(result: Results)

    @Query("SELECT * FROM student_results WHERE id = :id")
    fun getResult(id: Int): Results

    @Query("SELECT * FROM student_results")
    fun getAllResults(): LiveData<List<Results>>

    @Update
    fun updateResult(result: Results)

    @Query("DELETE FROM student_results WHERE id = :id")
    fun deleteResult(id: Int)

    @Query("DELETE FROM student_results")
    fun deleteAllResults()

}