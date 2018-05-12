package io.rodrigo.agimarveltest.model.network.response

import com.google.gson.annotations.SerializedName
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import java.util.*

data class CharacterResponseItem(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("modified")
        val dateCreated: Date?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("thumbnail")
        val thumbnail: ImageResponse?
) {
    fun toMarvelCharacter() = MarvelCharacter(
            id,
            name,
            dateCreated,
            if (description.isNullOrBlank()) null else description,
            thumbnail.toString()
    )
}