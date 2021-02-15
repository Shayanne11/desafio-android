package com.shayanne.desafioshayanne.repository


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shayanne.desafioshayanne.util.MockWebServerRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryInstrumentedTestActivityTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    // antes do teste de um delay
    //idling faz o delay para aguardar a info chegar
    /*   @Before
       fun setup() {
           repositoryArrange(mockWebServer) {
               registerIdlingResource()
           }
       }


       // depois do teste, desligue o sistema
       @After
       fun teardown() {
           repositoryArrange(mockWebServer) {
               shutdownServer()
           }
       }*/

    // testes instrumentados procuram por algo que é exibido na tela do celular
    //por isso usamos o startRepositoryScreen, para exibir a tela e o teste procurar o que foi pedido
    //no caso do teste de sucesso foi pedido para procurar o texto CS-Notes
    // o teste de sucesso:
    @Test
    fun givenRequestSucessful_shouldRenderRepositoriesList() {
        repositoryArrange(mockWebServerRule) {
            enqueueResponse("resposta_sucesso.json")
            //inicie a tela da activity:
            startRepositoryScreen()
        }
        Assert {
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
        //irá procurar a frase que colocamos para o usuario visualizar em caso de erro do servidor,
        //no caso é a frase Erro desconhecido que está no nosso arquivo de Strings e que usamos na viewModel
        Assert {
            checkTextVisible("Erro desconhecido")
        }
    }

    // Erro de conexão:
    @Test
    fun givenRequestFail_shouldShowRequestFailed() {
        // ao usar o mock em teste instrumentado vc nao testa o parse, por isso nao é recomendado
        repositoryArrange(mockWebServerRule) {
            //estamos dizendo o que queremos que o servidor responda
            enqueueMockServerRequestError(Throwable())
            //inicie a tela da activity:
            startRepositoryScreen()

            Assert {
                checkTextVisible("Ocorreu um erro ao processar sua requisição. Tentar novamente?")
            }
        }
    }

}