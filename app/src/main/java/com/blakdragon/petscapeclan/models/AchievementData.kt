package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.models.enums.AchievementType

data class AchievementData(
    val type: AchievementType,
    val label: String
)