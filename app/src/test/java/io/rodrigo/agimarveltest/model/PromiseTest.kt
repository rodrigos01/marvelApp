package io.rodrigo.agimarveltest.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PromiseTest {
    @Test
    fun shouldNotifySuccessLambda() {
        val promise = Promise<String> { _, _ -> }
        var value: String? = null
        promise.onSuccess { value = it }
        promise.fulfill("test")
        assertEquals("test", value)
    }

    @Test
    fun shouldNotifySuccessLambdaAfterValueIsSet() {
        val promise = Promise<String> { _, _ -> }
        var value: String? = null
        promise.fulfill("test")
        promise.onSuccess { value = it }
        assertEquals("test", value)
    }

    @Test
    fun shouldNotifyErrorLambda() {
        val promise = Promise<String> { _, _ -> }
        var errorMessage: String? = null
        promise.onError { t ->
            errorMessage = t.message
        }
        promise.reject(Throwable("error"))
        assertEquals("error", errorMessage)
    }

    @Test
    fun shouldNotifyErrorLambdaAfterErrorIsSet() {
        val promise = Promise<String> { _, _ -> }
        var errorMessage: String? = null
        promise.reject(Throwable("error"))
        promise.onError { t ->
            errorMessage = t.message
        }
        assertEquals("error", errorMessage)
    }
}