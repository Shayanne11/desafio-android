package com.shayanne.desafioshayanne.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shayanne.desafioshayanne.R
import com.shayanne.desafioshayanne.api.ApiWebClientRequest
import com.shayanne.desafioshayanne.model.PullRequests
import com.shayanne.desafioshayanne.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullViewModel(
    private val callGit: ApiWebClientRequest,
    private val logger: Logger
) : ViewModel() {

    private val state = MutableLiveData<PullViewState>()

    // esta é apenas uma LiveData, nao é mutable, pois nao queremos que a activity a altere
    fun getViewState(): LiveData<PullViewState> = state
    // owner e repository devem passar nesta ordem correta
    fun loadurl(owner: String, repository: String) {
        /* owner,repositorio*/
        callGit.getPullRequests(owner, repository)
            .enqueue(object : Callback<List<PullRequests>> {
                // ERRO DE CONEXÃO
                override fun onFailure(call: Call<List<PullRequests>>, t: Throwable) {
                    logger.logMessage("Erro", t.message.toString())
                    state.postValue(PullViewState.Erro(R.string.error_network_request_failed))
                }

                override fun onResponse(
                    call: Call<List<PullRequests>>,
                    response: Response<List<PullRequests>>
                ) { // RESPOSTA DE SUCESSO
                    if (response.isSuccessful) {
                        response.body()?.let {
                            state.postValue(PullViewState.Sucesso(it))
                        }
                    } else { // ERRO DE SERVIDOR
                        state.postValue(PullViewState.Erro(R.string.error_unknown))
                    }
                }
            })
    }
}
