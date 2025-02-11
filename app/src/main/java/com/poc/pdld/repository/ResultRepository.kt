
package com.poc.pdld.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.poc.pdld.data.Results
import com.poc.pdld.database.ResultDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await

class ResultRepository(
    private val resultDao: ResultDao
) {

    val foundStudent = MutableLiveData<Results>()
    private val firestore = FirebaseFirestore.getInstance()

    fun getAllResults(): LiveData<List<Results>> = resultDao.getAllResults()

    suspend fun addResult(addResults: Results) {
        withContext(Dispatchers.IO) {
            resultDao.addResult(addResults)
        }
    }

    suspend fun getResult(id: Int) {
        withContext(Dispatchers.IO) {
            val result = resultDao.getResult(id)
            foundStudent.postValue(result)
        }
    }

    suspend fun updateResult(updateResults: Results) {
        withContext(Dispatchers.IO) {
            resultDao.updateResult(updateResults)
        }
    }

    suspend fun deleteResults(deleteResults: Int) {
        withContext(Dispatchers.IO) {
            resultDao.deleteResult(deleteResults)
        }
    }

    suspend fun deleteAllResults() {
        withContext(Dispatchers.IO) {
            resultDao.deleteAllResults()
        }
    }


    suspend fun syncResultsWithFirestore() {
        val unsyncedResults = resultDao.getUnsyncedResults()

        for (result in unsyncedResults) {
            try {
                val timestamp = System.currentTimeMillis()
                firestore.collection("results").document(result.id.toString())
                    .set(result)
                    .await()
                resultDao.updateSyncStatus(result.id, true, timestamp)
            } catch (e: Exception) {
                Log.e("SyncError", "Failed to sync result ${result.id}: ${e.message}")
            }
        }
    }

    fun getLastSyncedTime(): LiveData<Long?> {
        return resultDao.getLastSynced()
    }

}
