package com.shayanne.desafioshayanne.viewmodel.viewmodelrepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shayanne.desafioshayanne.api.ApiWebClientRequest
import com.shayanne.desafioshayanne.utils.LoggerAndroid

class RepositoryViewModelFactory(private val callGit: ApiWebClientRequest) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryViewModel::class.java)) {

            return RepositoryViewModel(callGit, LoggerAndroid()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
