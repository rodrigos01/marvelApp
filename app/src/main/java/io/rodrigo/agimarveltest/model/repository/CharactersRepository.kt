package io.rodrigo.agimarveltest.model.repository

import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.ui.Listing

interface CharactersRepository {
    val characters: Listing<MarvelCharacter>
}