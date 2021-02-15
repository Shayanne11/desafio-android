package com.shayanne.desafioshayanne.viewmodel.viewmodelrepository

import com.shayanne.desafioshayanne.model.RepositoryRequests

sealed class RepositoryViewState {

// como o app deve se mostrar
    // erro = erro e sucesso = lista
    // sucesso  esconder o loading e prover a lista pro adapter e
    // tela nova, empty space, toast, snackbar, ....
    // empty state

    data class Sucesso(val list: List<RepositoryRequests>) : RepositoryViewState()
    data class Erro(val messageError: Int) : RepositoryViewState()
}
