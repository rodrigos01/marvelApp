package io.rodrigo.agimarveltest.model.network.adapter

import io.reactivex.Single
import io.rodrigo.agimarveltest.model.network.response.CharacterResponseItem
import io.rodrigo.agimarveltest.model.network.response.ComicResponseItem
import io.rodrigo.agimarveltest.model.network.response.ItemListResponse

interface NetworkAdapter {
    fun getCharacters(limit: Int = 30, offset: Int = 0): Single<ItemListResponse<CharacterResponseItem>>
    fun getComics(characterId: Int): Single<ItemListResponse<ComicResponseItem>>
}