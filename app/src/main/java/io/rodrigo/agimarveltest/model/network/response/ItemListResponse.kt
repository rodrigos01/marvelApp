package io.rodrigo.agimarveltest.model.network.response

import com.google.gson.annotations.SerializedName

data class ItemListResponse<T>(
        @SerializedName("total")
        val total: Int,
        @SerializedName("results")
        val results: List<T>
)