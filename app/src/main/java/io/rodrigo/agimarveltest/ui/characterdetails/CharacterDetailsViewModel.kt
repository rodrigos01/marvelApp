package io.rodrigo.agimarveltest.ui.characterdetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.extensions.dateString
import javax.inject.Inject

class CharacterDetailsViewModel(character: MarvelCharacter) : ViewModel() {

    val id = character.id
    val name = character.name
    val imageUrl = character.imageUrl
    val dateCreated = character.dateCreated?.dateString()
    val description = character.description

    class Factory @Inject constructor() : ViewModelProvider.Factory {

        lateinit var character: MarvelCharacter

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = CharacterDetailsViewModel(character) as T
    }
}