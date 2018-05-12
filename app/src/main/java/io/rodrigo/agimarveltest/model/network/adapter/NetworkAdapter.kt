package io.rodrigo.agimarveltest.model.network.adapter

import io.rodrigo.agimarveltest.model.Promise
import io.rodrigo.agimarveltest.model.network.response.CharacterResponseItem
import io.rodrigo.agimarveltest.model.network.response.ComicResponseItem
import io.rodrigo.agimarveltest.model.network.response.ItemListResponse

interface NetworkAdapter {
    fun getCharacters(limit: Int = 30, offset: Int = 0): Promise<ItemListResponse<CharacterResponseItem>>
    fun getComics(characterId: Int): Promise<ItemListResponse<ComicResponseItem>>
}