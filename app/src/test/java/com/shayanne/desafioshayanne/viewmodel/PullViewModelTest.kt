package com.shayanne.desafioshayanne.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shayanne.desafioshayanne.R
import com.shayanne.desafioshayanne.api.ApiWebClientRequest
import com.shayanne.desafioshayanne.model.Owner
import com.shayanne.desafioshayanne.model.PullRequests
import com.shayanne.desafioshayanne.viewmodel.viewmodelpull.PullViewModel
import com.shayanne.desafioshayanne.viewmodel.viewmodelpull.PullViewState
import io.mockk.every
import io.mockk.mockk
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.IOException

class PullViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val owner = ""
    private val repository = ""
    private val fakeLogger = FakeLogger()
    private val mockwebClient = mockk<ApiWebClientRequest>()
    private val viewModel = PullViewModel(mockwebClient,fakeLogger)



    // api respondeu com sucesso
    @Test
    fun givenResponseSuccessful_shouldFillResultListLiveData() {
        val resultList = createFakeReposList()
        // arrange
        every {
            mockwebClient.getPullRequests(owner,repository)
        } returns Calls.response(Response.success(resultList))

        // act
        viewModel.loadurl(owner,repository)

        // assert
        assertEquals(
            PullViewState.Sucesso(resultList),
            viewModel.getViewState().value
        )
    }

    // api respondeu que tem algum erro ( teste do else)
    @Test
    fun givenResponseError_shouldFillResultListLiveData() {
        val exceptionThrown = IOException()
        every {
            mockwebClient.getPullRequests(owner,repository)
        } returns Calls.response(Response.error(404,"".toResponseBody()))

        viewModel.loadurl(owner,repository)

        // assert
        assertEquals(
            PullViewState.Erro(R.string.error_unknown),
            viewModel.getViewState().value
        )
    }

    // api não respondeu
    @Test
    fun givenNetworkError_shouldReturnError() {
        val exceptionThrown = IOException()
        every {
            mockwebClient.getPullRequests(owner,repository)
        } returns Calls.failure(exceptionThrown)

        viewModel.loadurl(owner,repository)

        assertEquals(
            PullViewState.Erro(R.string.error_network_request_failed),
            viewModel.getViewState().value
        )
    }


    private fun createFakeReposList(): List<PullRequests> {
        return (1..10).map { number ->
            PullRequests(
                title = "name $number",
                body = "body $number",
                user= Owner( login = "joao",avatarUrl = "https://google.com"),
                htmlUrl = "html_url $number",
                fullName = "Joao $number"
            )
        }
    }
}