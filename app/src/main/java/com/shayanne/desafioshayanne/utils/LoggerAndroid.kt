package com.shayanne.desafioshayanne.utils

import android.util.Log

class LoggerAndroid : Logger {

    override fun logMessage(tag: String, message: String) {
        // faz:
        Log.d(tag, message)
    }
}
