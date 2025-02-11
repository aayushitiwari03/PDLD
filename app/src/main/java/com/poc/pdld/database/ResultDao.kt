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

    @Query("SELECT * FROM student_results WHERE is_synced = 0")
    suspend fun getUnsyncedResults(): List<Results>

    @Query("UPDATE student_results SET is_synced = :synced, last_synced = :lastSynced WHERE id = :id")
    suspend fun updateSyncStatus(id: Int, synced: Boolean, lastSynced: Long?)

    @Query("SELECT last_synced FROM student_results ORDER BY last_synced DESC LIMIT 1")
    fun getLastSynced(): LiveData<Long?>

}