package io.rodrigo.agimarveltest.model.network.response

import com.google.gson.annotations.SerializedName
import io.rodrigo.agimarveltest.model.data.Comic
import java.util.*

data class ComicResponseItem(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("modified")
        val releaseDate: Date?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("thumbnail")
        val thumbnail: ImageResponse?,
        @SerializedName("images")
        val images: List<ImageResponse>?,
        @SerializedName("creators")
        val creators: StaffResponse?
) {

    fun toComic() = Comic(
            id,
            title,
            releaseDate,
            description,
            thumbnail.toString(),
            images?.map { it.toString() },
            creators?.toStaffList()
    )

    data class StaffResponse(
            @SerializedName("items")
            val items: List<StaffResponseItem>
    ) {
        fun toStaffList() = items.map { it.toStaff() }
    }

    data class StaffResponseItem(
            @SerializedName("name")
            val name: String = "",
            @SerializedName("role")
            val role: String = ""
    ) {
        fun toStaff() = Comic.Staff(name, role)
    }
}