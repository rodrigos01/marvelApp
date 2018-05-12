package io.rodrigo.agimarveltest.model.network.adapter

import io.reactivex.Single
import io.rodrigo.agimarveltest.model.network.MarvelAPI
import io.rodrigo.agimarveltest.model.network.authorization.AuthorizationProvider
import io.rodrigo.agimarveltest.model.network.response.CharacterResponseItem
import io.rodrigo.agimarveltest.model.network.response.ComicResponseItem
import io.rodrigo.agimarveltest.model.network.response.ItemListResponse


class NetworkAdapterImpl(
        private val authorizationProvider: AuthorizationProvider,
        private val marvelAPI: MarvelAPI
) : NetworkAdapter {

    companion object {
        const val PUBLIC_KEY = "75e79a4beb64a1a1c411439f06457bd7"
        const val PRIVATE_KEY = "2e3fa0ad8250ceb96b82b10eec08a5f94162db99"
    }

    override fun getCharacters(limit: Int, offset: Int): Single<ItemListResponse<CharacterResponseItem>> {
        val authData = authorizationProvider.getAuthorizationData()
        return marvelAPI.getCharacters(authData.apiKey, authData.timestamp, authData.hash, limit, offset)
                .map { it.data }
    }

    override fun getComics(characterId: Int): Single<ItemListResponse<ComicResponseItem>> {
        val authData = authorizationProvider.getAuthorizationData()
        return marvelAPI.getCharacterComics(characterId, authData.apiKey, authData.timestamp, authData.hash)
                .map { it.data }
    }
}