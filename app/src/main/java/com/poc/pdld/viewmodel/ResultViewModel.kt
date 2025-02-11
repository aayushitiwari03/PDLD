package com.poc.pdld.viewmodel

import androidx.lifecycle.ViewModel
import com.poc.pdld.data.Results
import com.poc.pdld.repository.ResultRepository

class ResultViewModel(
    private val resultRepository: ResultRepository
) : ViewModel(){

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