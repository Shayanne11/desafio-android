package com.shayanne.desafioshayanne.util

import com.shayanne.desafioshayanne.api.InicializerApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule: TestWatcher() {

    val mockWebServer = MockWebServer()

    // inicia o teste
    override fun starting(description: Description?) {
        super.starting(description)

        mockWebServer.start(8080)
        InicializerApi.baseUrl = mockWebServer.url("/").toString()
    }

    //finaliza o teste
    override fun finished(description: Description?) {
        super.finished(description)
        mockWebServer.shutdown()
    }
}