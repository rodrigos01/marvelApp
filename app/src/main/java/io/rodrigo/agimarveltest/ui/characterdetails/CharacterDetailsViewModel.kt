package io.rodrigo.agimarveltest.ui.characterdetails

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.extensions.asLiveData
import io.rodrigo.agimarveltest.model.extensions.dateString
import io.rodrigo.agimarveltest.model.repository.ComicRepository
import java.text.DateFormat
import javax.inject.Inject

class CharacterDetailsViewModel(character: MarvelCharacter, comicRepository: ComicRepository) : ViewModel() {

    val id = character.id
    val name = character.name
    val imageUrl = character.imageUrl
    val dateCreated = character.dateCreated?.dateString(style = DateFormat.MEDIUM)
    val description = character.description

    val comics = comicRepository.getComics(character.id)
            .asLiveData()

    class Factory @Inject constructor(private val comicRepository: ComicRepository) : ViewModelProvider.Factory {

        lateinit var character: MarvelCharacter

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = CharacterDetailsViewModel(character, comicRepository) as T
    }
}