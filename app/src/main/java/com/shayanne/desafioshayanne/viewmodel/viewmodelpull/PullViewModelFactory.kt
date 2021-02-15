package com.shayanne.desafioshayanne.viewmodel.viewmodelpull

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shayanne.desafioshayanne.api.ApiWebClientRequest
import com.shayanne.desafioshayanne.utils.LoggerAndroid

class PullViewModelFactory(private val callGit: ApiWebClientRequest) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PullViewModel::class.java)) {
            return PullViewModel(callGit, LoggerAndroid()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
