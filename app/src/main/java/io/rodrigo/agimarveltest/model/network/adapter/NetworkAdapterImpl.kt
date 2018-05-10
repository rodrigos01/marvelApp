package io.rodrigo.agimarveltest.model.network.adapter

import io.reactivex.Single
import io.rodrigo.agimarveltest.model.network.MarvelAPI
import io.rodrigo.agimarveltest.model.network.authorization.AuthorizationProvider
import io.rodrigo.agimarveltest.model.network.response.CharactersResponse


class NetworkAdapterImpl(
        private val authorizationProvider: AuthorizationProvider,
        private val marvelAPI: MarvelAPI
) : NetworkAdaper {

    companion object {
        const val PUBLIC_KEY = "75e79a4beb64a1a1c411439f06457bd7"
        const val PRIVATE_KEY = "2e3fa0ad8250ceb96b82b10eec08a5f94162db99"
    }

    override fun getCharacters(limit: Int, offset: Int): Single<CharactersResponse> {
        val authData = authorizationProvider.getAuthorizationData()
        return marvelAPI.getCharacters(authData.apiKey, authData.timestamp, authData.hash, limit, offset)
                .map { it.data }
    }
}