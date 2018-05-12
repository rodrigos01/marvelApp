package io.rodrigo.agimarveltest.model.repository

import io.reactivex.Single
import io.rodrigo.agimarveltest.model.data.Comic
import io.rodrigo.agimarveltest.model.network.adapter.NetworkAdapter

class MarvelComicRepository(private val networkAdapter: NetworkAdapter) : ComicRepository {
    override fun getComics(characterId: Int): Single<List<Comic>> {
        return networkAdapter.getComics(characterId)
                .map { it.results.map { it.toComic() } }
    }
}