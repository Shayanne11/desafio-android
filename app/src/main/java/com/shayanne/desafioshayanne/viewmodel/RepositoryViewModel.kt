package com.shayanne.desafioshayanne.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shayanne.desafioshayanne.R
import com.shayanne.desafioshayanne.api.ApiWebClientRequest
import com.shayanne.desafioshayanne.model.ItemsRepositories
import com.shayanne.desafioshayanne.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(
    // private val callGit é onde chamamos a api
    private val callGit: ApiWebClientRequest,
    private val logger: Logger
) : ViewModel() {

    private val state = MutableLiveData<RepositoryViewState>()

    // esta é apenas uma LiveData, nao é mutable, pois nao queremos que a activity a altere
    fun getViewState(): LiveData<RepositoryViewState> = state

    fun loadpage(page: Int) {
        // paginacao
        callGit.getRepositories(page).enqueue(object : Callback<ItemsRepositories> {
            override fun onResponse(
                call: Call<ItemsRepositories>,
                response: Response<ItemsRepositories>
            ) {
                if (response.isSuccessful) {
                     logger.logMessage("MainActivity-loadMore", "isSucessfull, page : $page")
                    /*Log.d("MainActivity-loadMore", "isSucessfull, page : $page")*/
                    response.body()?.let {
                        state.postValue(RepositoryViewState.Sucesso(it.items))
                    }
                } else {
                    logger.logMessage(
                        "MainActivity-loadMore",
                        "is NOT sucessful - ${response.code()} + ${response.errorBody()?.string()}"
                    )
                    state.postValue(RepositoryViewState.Erro(R.string.error_unknown))
                }
            }

            override fun onFailure(call: Call<ItemsRepositories>, t: Throwable) {
                state.postValue(RepositoryViewState.Erro(R.string.error_network_request_failed))
            }
        })
    }
}
