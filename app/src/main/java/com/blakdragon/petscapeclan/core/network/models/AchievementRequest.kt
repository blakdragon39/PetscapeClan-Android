package com.blakdragon.petscapeclan.core.network.models

import com.blakdragon.petscapeclan.models.enums.AchievementType

data class AchievementRequest(
    val type: AchievementType
)