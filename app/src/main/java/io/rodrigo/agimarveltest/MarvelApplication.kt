package io.rodrigo.agimarveltest

import android.app.Application
import io.rodrigo.agimarveltest.dependencyinjection.AppComponent
import io.rodrigo.agimarveltest.dependencyinjection.DaggerAppComponent

class MarvelApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .build()
    }

}