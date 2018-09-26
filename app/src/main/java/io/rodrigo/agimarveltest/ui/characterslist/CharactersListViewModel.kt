package io.rodrigo.agimarveltest.ui.characterslist

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.extensions.map
import io.rodrigo.agimarveltest.model.repository.CharactersRepository
import io.rodrigo.agimarveltest.ui.Listing
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(repository: CharactersRepository) : ViewModel() {

    val characters = repository.characters

    val error = repository.characters.status.map { it == Listing.Status.STATUS_ERROR }

    fun retry() {
        characters.refresh()
    }

    val itemCallback = object : DiffUtil.ItemCallback<MarvelCharacter>() {
        override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter) = oldItem == newItem

    }

}