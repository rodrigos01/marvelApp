package io.rodrigo.agimarveltest.ui.characterslist

import android.arch.lifecycle.ViewModel
import android.support.v7.util.DiffUtil
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.repository.CharactersRepository
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(repository: CharactersRepository) : ViewModel() {

    val characters = repository.characters

    val itemCallback = object : DiffUtil.ItemCallback<MarvelCharacter>() {
        override fun areItemsTheSame(oldItem: MarvelCharacter?, newItem: MarvelCharacter?) = oldItem?.id == newItem?.id

        override fun areContentsTheSame(oldItem: MarvelCharacter?, newItem: MarvelCharacter?) = oldItem == newItem

    }

}