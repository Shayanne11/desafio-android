package com.shayanne.desafioshayanne.util
import androidx.test.platform.app.InstrumentationRegistry

//funcao que encontra e le o arquivo json que criamos
fun String.loadAsFixture(): String {
    val context = InstrumentationRegistry.getInstrumentation().context
    return context.assets
            //abre o arquivo:
        .open("fixtures/$this")
            //le o arquivo:
        .bufferedReader()
        .readText()
}



/*
object FileReader {


    fun String.loadAsFixture(): String {
        val context = InstrumentationRegistry.getInstrumentation().context
        return context.assets
            .open("fixtures/$this")
            .bufferedReader()
            .readText()
    }
    fun main() {
        "repositories.json".loadAsFixture()
    }
}*/
