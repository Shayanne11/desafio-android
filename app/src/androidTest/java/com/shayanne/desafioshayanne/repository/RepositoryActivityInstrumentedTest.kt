package com.shayanne.desafioshayanne.repository


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shayanne.desafioshayanne.util.MockWebServerRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryInstrumentedTestActivityTest {
    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    // o teste de sucesso:
    @Test
    fun givenRequestSucessful_shouldRenderRepositoriesList() {
        repositoryArrange(mockWebServerRule) {
            enqueueResponse("resposta_sucesso.json")
            //inicie a tela da activity:
            startRepositoryScreen()
        }
        Assert{
            checkTextVisible("CS-Notes")
        }
    }

    //Erro de Servidor
    @Test
    fun givenRequestFail_shouldShowUnkownError() {
        // ao usar o mock em teste instrumentado vc nao testa o parse, por isso nao é recomendado
        repositoryArrange(mockWebServerRule) {
            //estamos dizendo o que queremos que o servidor responda
            enqueueMockServerError()
            //inicie a tela da activity:
            startRepositoryScreen()
        }
        Assert{
            checkTextVisible("Erro desconhecido")
        }
    }

    // Erro de conexão:

}