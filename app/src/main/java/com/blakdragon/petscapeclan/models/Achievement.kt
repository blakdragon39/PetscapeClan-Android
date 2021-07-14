package com.blakdragon.petscapeclan.models

import android.os.Parcelable
import com.blakdragon.petscapeclan.core.network.models.AchievementRequest
import com.blakdragon.petscapeclan.models.enums.AchievementType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Achievement(
    val type: AchievementType,
    val label: String
) : Parcelable {

    fun toRequest(): AchievementRequest = AchievementRequest(type)
}
