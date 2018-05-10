package io.rodrigo.agimarveltest.model.network

import io.reactivex.Single
import io.rodrigo.agimarveltest.model.network.response.ApiResponse
import io.rodrigo.agimarveltest.model.network.response.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelAPI {
    @GET("characters")
    fun getCharacters(
            @Query("apikey")
            apiKey: String,
            @Query("ts")
            timestamp: Long,
            @Query("hash")
            hash: String,
            @Query("limit")
            limit: Int = 30,
            @Query("offset")
            offset: Int = 0
    ): Single<ApiResponse<CharactersResponse>>
}