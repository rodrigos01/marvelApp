package io.rodrigo.agimarveltest.model.repository

import android.support.annotation.VisibleForTesting
import io.reactivex.Single
import io.rodrigo.agimarveltest.model.data.CharacterList
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.model.hash.generateMd5
import io.rodrigo.agimarveltest.model.network.MarvelAPI
import io.rodrigo.agimarveltest.model.network.response.CharacterResponseItem
import io.rodrigo.agimarveltest.model.network.response.CharactersResponse
import java.util.*


class MarvelCharactersRepository(private val marvelAPI: MarvelAPI) : CharactersRepository {

    companion object {
        private const val PUBLIC_KEY = "75e79a4beb64a1a1c411439f06457bd7"
        private const val PRIVATE_KEY = "2e3fa0ad8250ceb96b82b10eec08a5f94162db99"
    }

    override fun getCharacters(limit: Int, offset: Int): Single<CharacterList> {
        val authData = getAuthenticationData()
        return marvelAPI.getCharacters(authData.apiKey, authData.timestamp, authData.hash, limit, offset)
                .map { convertResponse(it.data) }
    }

    @VisibleForTesting
    fun getAuthenticationData(): AuthenticationData {
        val timestamp = Date().time
        val hash = generateMd5("$timestamp$PRIVATE_KEY$PUBLIC_KEY")
        return AuthenticationData(PUBLIC_KEY, timestamp, hash)
    }

    private fun convertResponse(response: CharactersResponse): CharacterList {
        return CharacterList(response.results.map { convertResponseItem(it) }, response.total)
    }

    @VisibleForTesting
    fun convertResponseItem(item: CharacterResponseItem) = MarvelCharacter(
            item.id,
            item.name,
            item.description,
            item.thumbnail?.let { "${it.path}.${it.extension}" }
    )

    data class AuthenticationData(
            val apiKey: String,
            val timestamp: Long,
            val hash: String
    )
}