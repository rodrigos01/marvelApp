package io.rodrigo.agimarveltest.model.network.response

import com.google.gson.annotations.SerializedName

data class CharacterResponseItem (
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String?,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?
)