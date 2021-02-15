package com.shayanne.desafioshayanne.pull

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.shayanne.desafioshayanne.util.loadAsFixture
import com.shayanne.desafioshayanne.pull.PullActivity.Companion.OWNER
import com.shayanne.desafioshayanne.pull.PullActivity.Companion.REPOSITORY
import com.shayanne.desafioshayanne.util.HttpStatus
import com.shayanne.desafioshayanne.util.MockWebServerRule
import com.shayanne.desafioshayanne.util.retryer
import okhttp3.mockwebserver.MockResponse

class pullArrange(val mockWebServerRule: MockWebServerRule, action: pullArrange.() -> Unit){
    init {
        action.invoke(this)
    }

    /*fun registerIdlingResource(){
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
    }*/
    // pega o arquivo json que criamos
    fun enqueueResponse(responseFileName:String){
        mockWebServerRule.mockWebServer.enqueue(MockResponse().setBody(responseFileName.loadAsFixture()))
    }

    fun enqueueMockServerError(){
        mockWebServerRule.mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.STATUS_400))
    }

    fun enqueueMockServerRequestError(t: Throwable){
        mockWebServerRule.mockWebServer.enqueue(MockResponse())
    }

    // inicia a tela da activity
    fun startPullScreen(){
        val bundle = bundleOf(OWNER to "CyC2018", REPOSITORY to "CS-Notes")
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            PullActivity::class.java).apply {
            //
            putExtras(bundle)
        }
        ActivityScenario.launch<PullActivity>(intent)
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
      //  retryer {
            Espresso.onView(ViewMatchers.withText(text))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
       // }
    }

}
