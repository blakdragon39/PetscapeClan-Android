package com.blakdragon.petscapeclan.models

import android.os.Parcelable
import com.blakdragon.petscapeclan.core.network.models.PetRequest
import com.blakdragon.petscapeclan.models.enums.PetType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val type: PetType,
    val label: String
) : Parcelable {

    fun toRequest(): PetRequest = PetRequest(type)
}
