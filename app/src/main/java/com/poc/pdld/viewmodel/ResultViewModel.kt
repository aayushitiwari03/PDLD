package com.poc.pdld.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.poc.pdld.data.Results
import com.poc.pdld.repository.ResultRepository
import kotlinx.coroutines.launch

class ResultViewModel(
    private val resultRepository: ResultRepository
) : ViewModel() {

    //val foundResult: LiveData<Results> = resultRepository.foundStudent


    init {
        getLastSyncedTime()
    }

    fun getAllResults(): LiveData<List<Results>> = resultRepository.getAllResults()

    fun addResult(result: Results) {
        viewModelScope.launch {
            resultRepository.addResult(result)
        }
    }

    fun getLastSyncedTime(): LiveData<Long?> {
        return resultRepository.getLastSyncedTime()
    }

    suspend fun getResult(id: Int) = resultRepository.getResult(id)

    fun updateResult(result: Results) {
        viewModelScope.launch {
            resultRepository.updateResult(result)
        }
    }

    fun deleteResult(resultId: Int) {
        viewModelScope.launch {
            resultRepository.deleteResults(resultId)
        }
    }

    fun deleteAllResults() {
        viewModelScope.launch {
            resultRepository.deleteAllResults()
        }
    }

    fun syncWithFirestore() {
        viewModelScope.launch {
            resultRepository.syncResultsWithFirestore()
        }
    }
}

class ResultViewModelFactory(private val repository: ResultRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
