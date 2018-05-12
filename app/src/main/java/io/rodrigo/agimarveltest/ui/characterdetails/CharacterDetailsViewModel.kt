package io.rodrigo.agimarveltest.ui.characterdetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.rodrigo.agimarveltest.model.data.Comic
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.extensions.MutableLiveData
import io.rodrigo.agimarveltest.model.extensions.dateString
import io.rodrigo.agimarveltest.model.extensions.map
import io.rodrigo.agimarveltest.model.repository.ComicRepository
import java.text.DateFormat
import javax.inject.Inject

class CharacterDetailsViewModel(private val character: MarvelCharacter, private val comicRepository: ComicRepository) : ViewModel() {

    val id = character.id
    val name = character.name
    val imageUrl = character.imageUrl
    val dateCreated = character.dateCreated?.dateString(style = DateFormat.MEDIUM)
    val description = character.description

    init {
        loadComics()
    }

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _comics = MutableLiveData<List<Comic>>()
    val comics: LiveData<List<Comic>>
        get() = _comics

    fun retry() {
        _loading.postValue(true)
        loadComics()
    }

    private fun loadComics() {
        comicRepository.getComics(character.id)
                .onError {
                    _loading.postValue(false)
                    _error.postValue(true)
                }
                .onSuccess {
                    _loading.postValue(false)
                    _comics.postValue(it)
                }
    }

    val showComics = comics.map { it.isNotEmpty() }

    class Factory @Inject constructor(private val comicRepository: ComicRepository) : ViewModelProvider.Factory {

        lateinit var character: MarvelCharacter

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = CharacterDetailsViewModel(character, comicRepository) as T
    }
}