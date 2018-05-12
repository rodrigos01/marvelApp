package io.rodrigo.agimarveltest.model.repository

import io.rodrigo.agimarveltest.model.Promise
import io.rodrigo.agimarveltest.model.data.Comic
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdapter

class MarvelComicRepository(private val networkAdapter: NetworkAdapter) : ComicRepository {
    override fun getComics(characterId: Int): Promise<List<Comic>> {
        return networkAdapter.getComics(characterId)
                .map { it.results.map { it.toComic() } }
    }
}