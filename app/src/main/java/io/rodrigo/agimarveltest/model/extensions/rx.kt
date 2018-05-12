package io.rodrigo.agimarveltest.model.extensions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.Single

fun <T> Single<T>.asLiveData(): LiveData<T> = LiveDataReactiveStreams.fromPublisher(this.toFlowable())