package io.rodrigo.agimarveltest.model.repository

import io.reactivex.Single
import io.rodrigo.agimarveltest.model.data.CharacterList
import io.rodrigo.agimarveltest.model.data.MarvelCharacter

interface CharactersRepository {
    fun getCharacters(limit: Int = 30, offset: Int = 0): Single<CharacterList>
}