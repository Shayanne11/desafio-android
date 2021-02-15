package com.shayanne.desafioshayanne.repository


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.shayanne.desafioshayanne.util.HttpStatus
import com.shayanne.desafioshayanne.api.InicializadorApi
import com.shayanne.desafioshayanne.util.MockWebServerRule
import com.shayanne.desafioshayanne.util.loadAsFixture

import com.shayanne.desafioshayanne.util.retryer
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.endsWith


class repositoryArrange(
    val mockWebServerRule: MockWebServerRule,
    action: repositoryArrange.() -> Unit) {

    init {
        action.invoke(this)
    }
    // chama o idling
   /* fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create("okhttp", InicializadorApi.okHttpClient)
        )
    }*/
    /*// inica o servidor
    fun startServer() {
        mockWebServer.start(8080)
        InicializadorApi.baseUrl = mockWebServer.url("/").toString()
    }*/
    // desliga o servidor
    /*fun  shutdownServer(){
        mockWebServer.shutdown()
    }*/

    // pega o arquivo json que criamos
    //retorna uma resposta de sucesso
    fun enqueueResponse(responseFileName:String){
        mockWebServerRule.mockWebServer.enqueue(MockResponse().setBody(responseFileName.loadAsFixture()))
    }

    //retorna resposta de erro do servidor
    fun enqueueMockServerError(){
        mockWebServerRule.mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.STATUS_400))
    }

    fun enqueueMockServerRequestError(t: Throwable){
        mockWebServerRule.mockWebServer.enqueue(MockResponse())
    }

    // inicia a tela da activity
    fun startRepositoryScreen(){
        ActivityScenario.launch(RepositoryActivity::class.java)
    }
}

//o nosso act nao faz nada pois só estamos checando info
class Act(action: Act.() -> Unit){
    init {
        action.invoke(this)
    }
}

// verifica se o CS-Notes foi chamado na tela:
class Assert(action: Assert.() -> Unit){
    init {
        action.invoke(this)
    }
    fun checkTextVisible(text :String){
        //o retryer tenta chamar o servidor e contém  o delay, vide com command +b
       // retryer {
          onView(withText(text)).check(matches(isDisplayed()))
   //     onView(withText(endsWith(text))).check(matches(isDisplayed()))
     //   }
    }

}