package io.rodrigo.agimarveltest.model.repository

import io.reactivex.Single
import io.rodrigo.agimarveltest.model.data.Comic

interface ComicRepository {
    fun getComics(characterId: Int): Single<List<Comic>>
}