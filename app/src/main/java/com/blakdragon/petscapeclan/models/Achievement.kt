package com.blakdragon.petscapeclan.models

import android.os.Parcelable
import com.blakdragon.petscapeclan.models.enums.AchievementType
import kotlinx.parcelize.Parcelize

@Parcelize
class Achievement(
    val type: AchievementType
) : Parcelable
