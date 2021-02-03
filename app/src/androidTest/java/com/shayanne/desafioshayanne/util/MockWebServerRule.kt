package com.shayanne.desafioshayanne.util

import androidx.test.espresso.IdlingRegistry
import com.shayanne.desafioshayanne.api.InicializadorApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule: TestWatcher() {

    val mockWebServer = MockWebServer()

    // inicia o teste
    override fun starting(description: Description?) {
        super.starting(description)
        //Idling
    IdlingRegistry.getInstance().register(OkHttp3IdlingResourceAndroidX.create("OkHttp",InicializadorApi.okHttpClient))
        mockWebServer.start(8080)
        InicializadorApi.baseUrl = mockWebServer.url("/").toString()
    }

    //finaliza o teste
    override fun finished(description: Description?) {
        super.finished(description)
        mockWebServer.shutdown()
    }
}