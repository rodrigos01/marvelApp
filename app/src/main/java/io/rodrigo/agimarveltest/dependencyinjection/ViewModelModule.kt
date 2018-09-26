package io.rodrigo.agimarveltest.dependencyinjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.rodrigo.agimarveltest.ui.ViewModelFactory
import io.rodrigo.agimarveltest.ui.characterslist.CharactersListViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersListViewModel::class)
    abstract fun bindCharacterListViewModel(charactersListViewModel: CharactersListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}