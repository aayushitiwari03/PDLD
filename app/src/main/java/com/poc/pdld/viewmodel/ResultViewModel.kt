package com.poc.pdld.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.poc.pdld.data.Results
import com.poc.pdld.repository.ResultRepository

class ResultViewModel(
    private val resultRepository: ResultRepository
) : ViewModel(), Lazy<ResultViewModel> {

    override val value: ResultViewModel
        get() = this

    override fun isInitialized(): Boolean {
        return true
    }

    val allStudent = resultRepository.getAllResults()
    val foundResult = resultRepository.foundStudent

    fun getAllResults() = resultRepository.getAllResults()

    fun addResult(result: Results) {
        resultRepository.addResult(result)
        getAllResults()
    }

    fun getResult(id: Int) = resultRepository.getResult(id)

    fun updateResult(result: Results) {
        resultRepository.updateResult(result)
        getAllResults()
    }

    fun deleteResult(result: Results) {
        resultRepository.deleteResults(result)
        getAllResults()
    }

    fun deleteAllResults() = resultRepository.deleteAllResults()

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