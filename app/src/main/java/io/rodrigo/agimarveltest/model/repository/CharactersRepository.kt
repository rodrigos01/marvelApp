package io.rodrigo.agimarveltest.model.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import io.rodrigo.agimarveltest.model.data.MarvelCharacter

interface CharactersRepository {
    val characters: LiveData<PagedList<MarvelCharacter>>
}