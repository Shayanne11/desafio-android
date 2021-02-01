package com.shayanne.desafioshayanne.Pull

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.shayanne.desafioshayanne.api.InicializadorApi
import com.shayanne.desafioshayanne.util.loadAsFixture
import com.shayanne.desafioshayanne.pull.PullActivity
import com.shayanne.desafioshayanne.util.retryer
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class pullArrange(val mockWebServer: MockWebServer, action: pullArrange.() -> Unit){
    init {
        action.invoke(this)
    }

    fun registerIdlingResource(){
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create("okhttp", InicializadorApi.okHttpClient)
        )
    }
    // inica o servidor
    fun startServer() {
        mockWebServer.start(8080)
        InicializadorApi.baseUrl = mockWebServer.url("/").toString()
    }
    // desliga o servidor
    fun  shutdownServer(){
        mockWebServer.shutdown()
    }
    // pega o arquivo json que criamos
    fun enqueueResponse(responseFileName:String){
        mockWebServer.enqueue(MockResponse().setBody(responseFileName.loadAsFixture()))
    }
    // inicia a tela da activity
    fun startRepositoryScreen(){
        ActivityScenario.launch(PullActivity::class.java)
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
        retryer {
            Espresso.onView(ViewMatchers.withText(text))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

}
