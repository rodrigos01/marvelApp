package io.rodrigo.agimarveltest.model

import android.support.annotation.VisibleForTesting
import java.util.*

class Promise<T>(block: (fulfill: (T) -> Unit, reject: (Throwable) -> Unit) -> Unit) {

    companion object {
        fun <T> just(value: T) = Promise<T> { fulfill, _ ->
            fulfill(value)
        }
    }

    var value: T? = null
    var error: Throwable? = null

    private val onFulfilledListeners = LinkedList<(T) -> Unit>()
    private val onErrorListeners = LinkedList<(Throwable) -> Unit>()

    init {
        block(this::fulfill, this::reject)
    }

    @VisibleForTesting
    fun fulfill(value: T) {
        this.value = value
        while (onFulfilledListeners.isNotEmpty()) {
            onFulfilledListeners.remove().invoke(value)
        }
    }

    @VisibleForTesting
    fun reject(error: Throwable) {
        this.error = error
        while (onErrorListeners.isNotEmpty()) {
            onErrorListeners.remove().invoke(error)
        }
    }

    fun onSuccess(listener: (T) -> Unit): Promise<T> {
        val currentValue = value
        if (currentValue != null) {
            listener(currentValue)
        } else {
            onFulfilledListeners.add(listener)
        }
        return this
    }

    fun onError(listener: (Throwable) -> Unit): Promise<T> {
        val currentValue = error
        if (currentValue != null) {
            listener(currentValue)
        } else {
            onErrorListeners.add(listener)
        }
        return this
    }

    fun <R> map(block: (T) -> (R)) = Promise<R> { fulfill, reject ->
        onSuccess {
            fulfill(block(it))
        }
        onError(reject)
    }

    fun <R> then(block: (T) -> Promise<R>) = Promise<R> { fulfill, reject ->
        onSuccess {
            block(it)
                    .onSuccess { fulfill(it) }
                    .onError { throwable -> reject(throwable) }
        }
        onError { throwable -> reject(throwable) }
    }

}