package io.rodrigo.agimarveltest.model.extensions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.rodrigo.agimarveltest.model.Promise

fun <T> Promise<T>.asLiveData(): LiveData<T> {
    val liveData = MutableLiveData<T>()
    this.onSuccess { liveData.postValue(it) }
    return liveData
}