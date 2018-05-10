package io.rodrigo.agimarveltest.model.network.response

import com.google.gson.annotations.SerializedName

data class Thumbnail(
        @SerializedName("path")
        val path: String = "",
        @SerializedName("extension")
        val extension: String = ""
)