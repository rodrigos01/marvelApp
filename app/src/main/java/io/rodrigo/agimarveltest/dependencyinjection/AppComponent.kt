package io.rodrigo.agimarveltest.dependencyinjection

import dagger.Component
import io.rodrigo.agimarveltest.ui.characterslist.CharacterListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(characterListFragment: CharacterListFragment)
}