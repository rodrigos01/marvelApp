package io.rodrigo.agimarveltest.model.repository

import io.rodrigo.agimarveltest.model.Promise
import io.rodrigo.agimarveltest.model.data.Comic

interface ComicRepository {
    fun getComics(characterId: Int): Promise<List<Comic>>
}