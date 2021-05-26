package com.blakdragon.petscapeclan.models

import android.os.Parcelable
import com.blakdragon.petscapeclan.models.enums.AchievementType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Achievement(
    val type: AchievementType
) : Parcelable {

    override fun equals(other: Any?): Boolean = (other as? Achievement)?.type == type

    override fun hashCode(): Int = type.hashCode()
}
