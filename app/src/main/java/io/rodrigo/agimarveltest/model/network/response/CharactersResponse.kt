package io.rodrigo.agimarveltest.model.network.response

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
        @SerializedName("total")
        val total: Int,
        @SerializedName("results")
        val results: List<CharacterResponseItem>
)