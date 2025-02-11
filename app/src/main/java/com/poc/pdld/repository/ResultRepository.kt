package com.poc.pdld.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.poc.pdld.data.Results
import com.poc.pdld.database.ResultDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultRepository(
    private val resultDao: ResultDao
) {

    val allStudent = MutableLiveData<List<Results>>()
    val foundStudent = MutableLiveData<Results>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addResult(addResults: Results) {
        coroutineScope.launch(Dispatchers.IO) {
            resultDao.addResult(addResults)
        }
    }

    fun getResult (id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            val result = resultDao.getResult(id)
            foundStudent.postValue(result)
        }
    }

    fun getAllResults(): LiveData<List<Results>> {
        return resultDao.getAllResults()
    }

    fun updateResult(updateResults: Results) {
        coroutineScope.launch(Dispatchers.IO) {
            resultDao.updateResult(updateResults)
        }
    }

    fun deleteResults(deleteResults: Results) {
        coroutineScope.launch(Dispatchers.IO) {
            resultDao.deleteResult(deleteResults)
        }
    }

    fun deleteAllResults() {
        coroutineScope.launch(Dispatchers.IO) {
            resultDao.deleteAllResults()
        }
    }


}