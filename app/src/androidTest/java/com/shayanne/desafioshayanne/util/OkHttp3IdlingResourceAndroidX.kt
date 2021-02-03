package com.shayanne.desafioshayanne.util
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
class OkHttp3IdlingResourceAndroidX private constructor(
    private val name: String,
    private val dispatcher: Dispatcher
) : IdlingResource {
    @Volatile
    var callback: ResourceCallback? = null
    override fun getName() = name
    override fun isIdleNow(): Boolean {
        val idle = dispatcher.runningCallsCount() == 0
        if (idle && callback != null) callback?.onTransitionToIdle()
        return idle
    }
    override fun registerIdleTransitionCallback(callback: ResourceCallback) {
        this.callback = callback
    }
    companion object {
        /**
         * Create a new [IdlingResource] from `client` as `name`. You must register
         * this instance using `Espresso.registerIdlingResources`.
         */
        fun create(name: String, client: OkHttpClient): OkHttp3IdlingResourceAndroidX {
            return OkHttp3IdlingResourceAndroidX(name, client.dispatcher)
        }
    }
    init {
        dispatcher.idleCallback = Runnable {
            val callback = callback
            callback?.onTransitionToIdle()
        }
    }
}