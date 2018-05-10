package io.rodrigo.agimarveltest.model.network.adapter

import io.reactivex.Single
import io.rodrigo.agimarveltest.model.network.response.CharactersResponse

interface NetworkAdaper {
    fun getCharacters(limit: Int = 30, offset: Int = 0): Single<CharactersResponse>
}