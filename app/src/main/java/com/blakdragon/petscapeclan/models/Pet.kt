package com.blakdragon.petscapeclan.models

import android.os.Parcelable
import com.blakdragon.petscapeclan.models.enums.PetType
import kotlinx.parcelize.Parcelize

@Parcelize
class Pet(
    val type: PetType
) : Parcelable {

    override fun equals(other: Any?): Boolean = (other as? Pet)?.type == type

    override fun hashCode(): Int = type.hashCode()
}
