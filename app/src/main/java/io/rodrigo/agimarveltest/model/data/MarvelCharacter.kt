package io.rodrigo.agimarveltest.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MarvelCharacter(
        val id: Int,
        val name: String,
        val dateCreated: Date?,
        val description: String?,
        val imageUrl: String?
) : Parcelable