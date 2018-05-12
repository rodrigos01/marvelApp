package io.rodrigo.agimarveltest.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Comic(
        val id: Int,
        val title: String,
        val releaseDate: Date?,
        val description: String?,
        val cover: String?,
        val images: List<String>?,
        val creators: List<Staff>?
) : Parcelable {

    @Parcelize
    data class Staff(
            val name: String,
            val role: String
    ) : Parcelable
}